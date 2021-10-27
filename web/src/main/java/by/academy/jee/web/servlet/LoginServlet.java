package by.academy.jee.web.servlet;

import by.academy.jee.util.Initializer;
import by.academy.jee.model.person.Person;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.web.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.academy.jee.web.constant.Constant.*;

@WebServlet(value = {"/", "/login"})
public class LoginServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (SessionUtil.getSessionUser(req) != null) {
            SessionUtil.setupForward(this, req, resp, "/jsp/common/alreadyLoggedIn.jsp");
            return;
        }
        SessionUtil.setupForward(this, req, resp, LOGIN_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        Person user = Initializer.adminDao.read(userName); // TODO - add check in studentDao
        if (user == null) {
            user = Initializer.teacherDao.read(userName);
            if (user == null) {
                log.info("Error - no user {} in database", userName);
                req.setAttribute(ERROR_MESSAGE, NO_SUCH_USER_IN_DATABASE);
                SessionUtil.setupForward(this, req, resp, LOGIN_JSP_URL);
                return;
            }
        }
        boolean isCorrectPassword = PasswordHasher.authenticate(password, user.getPwd(), user.getSalt());
        if (!isCorrectPassword) {
            log.info("someone tried to enter as user {} with wrong password", userName);
            String errorMessage = "Wrong password";
            req.setAttribute(ERROR_MESSAGE, errorMessage);
            SessionUtil.setupForward(this, req, resp, LOGIN_JSP_URL);
            return;
        }
        SessionUtil.setSessionUser(req, user);
        String role = user.getRole().toString();
        log.info("User {} is successfully authorised, role - {}", userName, role);
        switch (role) {
            case "ADMIN":
                SessionUtil.setupForward(this, req, resp, "/jsp/admin/adminMenu.jsp");
                break;
            case "TEACHER":
                SessionUtil.setupForward(this, req, resp, "/jsp/teacher/teacherMenu.jsp");
                break;
            case "STUDENT":
                SessionUtil.setupForward(this, req, resp, "/jsp/student/studentMenu.jsp");
                break;
            default:
                String errorMessage = "Error - role is filled incorrectly. Please contact admin to fix it";
                req.setAttribute(ERROR_MESSAGE, errorMessage);
                SessionUtil.setupForward(this, req, resp, LOGIN_JSP_URL);
        }
    }
}
