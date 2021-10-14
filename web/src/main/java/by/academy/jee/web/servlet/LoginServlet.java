package by.academy.jee.web.servlet;

import by.academy.jee.util.Initializer;
import by.academy.jee.model.person.Person;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.web.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/", "/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (SessionUtil.getSessionUser(req) != null) {
            SessionUtil.setupForward(this, req, resp, "/jsp/common/alreadyLoggedIn.jsp");
            return;
        }
        SessionUtil.setupForward(this, req, resp, "/jsp/common/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        Person user = Initializer.personDao.read(userName);
        if (user == null) {
            String errorMessage = "No such user in database";
            req.setAttribute("errorMessage", errorMessage);
            SessionUtil.setupForward(this, req, resp, "/jsp/common/login.jsp");
            return;
        }
        boolean isCorrectPassword = PasswordHasher.authenticate(password, user.getPwd(), user.getSalt());
        if (!isCorrectPassword) {
            String errorMessage = "Wrong password";
            req.setAttribute("errorMessage", errorMessage);
            SessionUtil.setupForward(this, req, resp, "/jsp/common/login.jsp");
            return;
        }
        SessionUtil.setSessionUser(req, user);
        String role = user.getRole().toString();
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
                req.setAttribute("errorMessage", errorMessage);
                SessionUtil.setupForward(this, req, resp, "/jsp/common/login.jsp");
        }
    }
}
