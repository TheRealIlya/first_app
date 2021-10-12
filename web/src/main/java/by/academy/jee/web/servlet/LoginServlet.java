package by.academy.jee.web.servlet;

import by.academy.jee.util.Initializer;
import by.academy.jee.model.person.Person;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.web.util.SessionUtil;

import javax.servlet.RequestDispatcher;
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
            RequestDispatcher dispatcher = getServletContext().
                    getRequestDispatcher("/jsp/common/alreadyLoggedIn.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher("/jsp/common/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        Person user = Initializer.personDao.read(userName);
        if (user == null) {
            String errorMessage = "No such user in database";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = getServletContext().
                    getRequestDispatcher("/jsp/common/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        boolean isCorrectPassword = PasswordHasher.authenticate(password, user.getPwd(), user.getSalt());
        if (!isCorrectPassword) {
            String errorMessage = "Wrong password";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = getServletContext().
                    getRequestDispatcher("/jsp/common/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        SessionUtil.setSessionUser(req, user);
        String role = user.getRole().toString();
        switch (role) {
            case "ADMIN":
                RequestDispatcher dispatcher = getServletContext().
                        getRequestDispatcher("/jsp/admin/adminMenu.jsp");
                dispatcher.forward(req, resp);
                break;
            case "TEACHER": //TODO teacher jsp
                break;
            case "STUDENT": //TODO student jsp
                break;
            default:
                String errorMessage = "Error - role is filled incorrectly. Please contact admin to fix it";
                req.setAttribute("errorMessage", errorMessage);
                dispatcher = getServletContext().
                        getRequestDispatcher("/jsp/common/login.jsp");
                dispatcher.forward(req, resp);
        }
    }
}
