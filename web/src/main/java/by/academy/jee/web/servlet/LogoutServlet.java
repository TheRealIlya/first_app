package by.academy.jee.web.servlet;

import by.academy.jee.web.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.LOGIN_JSP_URL;
import static by.academy.jee.web.constant.Constant.SUCCESSFULLY_LOGGED_OUT;
import static by.academy.jee.web.constant.Constant.USER_IS_LOGGED_OUT;

@WebServlet(value = "/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        log.info(USER_IS_LOGGED_OUT);
        req.setAttribute(ERROR_MESSAGE, SUCCESSFULLY_LOGGED_OUT);
        SessionUtil.setupForward(this, req, resp, LOGIN_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
