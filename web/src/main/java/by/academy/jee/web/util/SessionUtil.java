package by.academy.jee.web.util;

import by.academy.jee.model.person.Person;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {

    public static void setSessionUser(HttpServletRequest req, Person user) {
        req.getSession().setAttribute("user", user);
    }

    public static Person getSessionUser(HttpServletRequest req) {
        return (Person) req.getSession().getAttribute("user");
    }

}
