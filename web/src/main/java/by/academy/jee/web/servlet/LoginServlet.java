package by.academy.jee.web.servlet;

import by.academy.jee.Initializer;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Person;
import by.academy.jee.util.PasswordHasher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().
                getRequestDispatcher("/jsp/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        Person user = Initializer.personDao.read(userName);
        if (user == null) {
            String errorMessage = "No such user in database";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = getServletContext().
                    getRequestDispatcher("/jsp/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        boolean isCorrectPassword = PasswordHasher.authenticate(password, user.getPwd(), user.getSalt());
        if (!isCorrectPassword) {
            String errorMessage = "Wrong password";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = getServletContext().
                    getRequestDispatcher("/jsp/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        String role = user.getRole().toString();
        switch (role) {
            case "ADMIN": //TODO admin jsp
                break;
            case "TEACHER": //TODO teacher jsp
                break;
            case "STUDENT": //TODO student jsp
                break;
            default:
                String errorMessage = "Error - role is filled incorrectly. Please contact admin to fix it";
                req.setAttribute("errorMessage", errorMessage);
                RequestDispatcher dispatcher = getServletContext().
                        getRequestDispatcher("/jsp/login.jsp");
                dispatcher.forward(req, resp);
        }
    }
}
