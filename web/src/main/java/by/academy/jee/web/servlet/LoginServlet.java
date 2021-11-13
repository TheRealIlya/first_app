package by.academy.jee.web.servlet;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.web.service.Service;
import by.academy.jee.web.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static by.academy.jee.web.constant.Constant.ALREADY_LOGGED_IN_JSP_URL;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.LOGIN_JSP_URL;
import static by.academy.jee.web.constant.Constant.PASSWORD;
import static by.academy.jee.web.constant.Constant.USER_NAME;

@WebServlet(value = {"/", "/login"})
public class LoginServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (SessionUtil.getSessionUser(req) != null) {
            SessionUtil.setupForward(this, req, resp, ALREADY_LOGGED_IN_JSP_URL);
            return;
        }
        SessionUtil.setupForward(this, req, resp, LOGIN_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String userName = req.getParameter(USER_NAME);
            Person user = Service.getUserIfExist(userName);
            String password = req.getParameter(PASSWORD);
            Service.checkPassword(password, user);
            SessionUtil.setSessionUser(req, user);
            String role = user.getRole().toString();
            log.info("User {} is successfully authorised, role - {}", user.getLogin(), role);
            String menuUrl = Service.getMenuUrlAfterLogin(role);
            SessionUtil.setupForward(this, req, resp, menuUrl);
        } catch (ServiceException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, LOGIN_JSP_URL);
        }
    }
}
