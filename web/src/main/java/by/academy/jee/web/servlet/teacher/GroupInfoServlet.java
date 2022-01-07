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
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.GROUP_INFO_ERROR_PAGE_JSP_URL;
import static by.academy.jee.web.constant.Constant.GROUP_INFO_JSP_URL;
import static by.academy.jee.web.constant.Constant.USER;

@Slf4j
@WebServlet(value = "/groupInfo")
public class GroupInfoServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Person person = (Person) req.getSession().getAttribute(USER);
            service.checkIsNotATeacher(person);
            Teacher teacher = (Teacher) person;
            Group group = service.getGroupByTeacher(teacher);
            SessionUtil.setSessionGroup(req, group);
            SessionUtil.setupForward(this, req, resp, GROUP_INFO_JSP_URL);
        } catch (ServiceException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, GROUP_INFO_ERROR_PAGE_JSP_URL);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
