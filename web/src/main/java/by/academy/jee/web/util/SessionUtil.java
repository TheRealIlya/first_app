package by.academy.jee.web.util;

import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Person;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static by.academy.jee.web.constant.Constant.USER;

public class SessionUtil {

    private SessionUtil() {
        //util class
    }

    public static void setSessionUser(HttpServletRequest req, Person user) {
        req.getSession().setAttribute(USER, user);
    }

    public static Person getSessionUser(HttpServletRequest req) {
        return (Person) req.getSession().getAttribute(USER);
    }

    public static void setSessionGroup(HttpServletRequest req, Group group) {
        req.getSession().setAttribute("group", group);
    }

    public static Group getSessionGroup(HttpServletRequest req) {
        return (Group) req.getSession().getAttribute("group");
    }

    public static void setupForward(HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp, String url)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }

    public static void setupInclude(HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp, String url)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(url);
        dispatcher.include(req, resp);
    }

}
