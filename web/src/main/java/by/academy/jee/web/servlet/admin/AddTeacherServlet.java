package by.academy.jee.web.servlet.admin;

import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.servlet.AbstractServlet;
import by.academy.jee.web.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.academy.jee.web.constant.Constant.ADD_TEACHER_JSP_URL;
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.TEACHER_IS_SUCCESSFULLY_ADDED;

@Slf4j
@WebServlet(value = "/addTeacher")
public class AddTeacherServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Teacher teacher = service.getTeacherFromRequest(req);
            service.checkIsUserNotExist(teacher.getLogin());
            service.createTeacherAfterChecks(teacher);
            log.info("Teacher {} is successfully added", teacher.getLogin());
            req.setAttribute(APPROVE_MESSAGE, TEACHER_IS_SUCCESSFULLY_ADDED);
            SessionUtil.setupInclude(this, req, resp, ADD_TEACHER_JSP_URL);
        } catch (ServiceException | DaoException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
        }
    }
}
