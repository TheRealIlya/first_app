package by.academy.jee.web.util;

import by.academy.jee.model.person.Person;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionUtil {

    public static void setSessionUser(HttpServletRequest req, Person user) {
        req.getSession().setAttribute("user", user);
    }

    public static Person getSessionUser(HttpServletRequest req) {
        return (Person) req.getSession().getAttribute("user");
    }

    public static void setupForward(HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp, String url)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }

}
