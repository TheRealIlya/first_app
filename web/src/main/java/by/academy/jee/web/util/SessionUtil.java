package by.academy.jee.web.util;

import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Person;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static by.academy.jee.web.constant.Constant.GROUP;
import static by.academy.jee.web.constant.Constant.USER;

public class SessionUtil {

    private SessionUtil() {
        //util class
    }

    public static Person getSessionUser(HttpSession httpSession) {
        return (Person) httpSession.getAttribute(USER);
    }

    public static Person getSessionUser(HttpServletRequest req) {
        return getSessionUser(getSessionFromRequest(req));
    }

    public static Group getSessionGroup(HttpSession httpSession) {
        return (Group) httpSession.getAttribute(GROUP);
    }

    private static HttpSession getSessionFromRequest(HttpServletRequest req) {
        return req.getSession();
    }

}
