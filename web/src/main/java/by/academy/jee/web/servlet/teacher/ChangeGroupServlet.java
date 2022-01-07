package by.academy.jee.web.servlet.teacher;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.servlet.AbstractServlet;
import by.academy.jee.web.util.SessionUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.CHANGE_GROUP_JSP_URL;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.TEACHER_MENU_JSP_URL;
import static by.academy.jee.web.constant.Constant.USER;

@WebServlet(value = "/changeGroup")
public class ChangeGroupServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, CHANGE_GROUP_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Group oldGroup = SessionUtil.getSessionGroup(req);
            String newGroupTitle = req.getParameter("title");
            Person person = (Person) req.getSession().getAttribute(USER);
            service.checkIsNotATeacher(person);
            Teacher teacher = (Teacher) person;
            service.changeGroup(oldGroup, newGroupTitle, teacher);
            req.setAttribute(APPROVE_MESSAGE, "Your group has been changed");
            SessionUtil.setupForward(this, req, resp, TEACHER_MENU_JSP_URL);
        } catch (ServiceException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, CHANGE_GROUP_JSP_URL);
        }
    }
}
