package by.academy.jee.web.servlet.admin;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.PersonDaoFactory;
import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.web.service.Service;
import by.academy.jee.web.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.academy.jee.web.constant.Constant.ADD_TEACHER_JSP_URL;
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.TEACHER_IS_SUCCESSFULLY_ADDED;

@WebServlet(value = "/addTeacher")
public class AddTeacherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(AddTeacherServlet.class);

    private static PersonDao<Teacher> teacherDao = PersonDaoFactory.getPersonDao(Role.TEACHER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Teacher teacher = Service.getTeacherFromRequest(req);
            Service.checkIsUserNotExist(teacher.getLogin());
            teacherDao.create(teacher);
            log.info("Teacher {} is successfully added", teacher.getLogin());
            req.setAttribute(APPROVE_MESSAGE, TEACHER_IS_SUCCESSFULLY_ADDED);
            SessionUtil.setupInclude(this, req, resp, ADD_TEACHER_JSP_URL);
        } catch (ServiceException | DaoException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
        }
    }
}
