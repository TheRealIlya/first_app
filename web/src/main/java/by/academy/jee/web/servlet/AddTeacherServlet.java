package by.academy.jee.web.servlet;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.util.Initializer;
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

@WebServlet(value = "/addTeacher")
public class AddTeacherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(AddTeacherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Teacher teacher = Service.getTeacherFromRequest(req);
            Service.checkIsUserNotExist(teacher.getLogin());
            Initializer.getTeacherDao().create(teacher);
            log.info("Teacher {} is successfully added", teacher.getLogin());
            String approveMessage = "Teacher is successfully added!";
            req.setAttribute(APPROVE_MESSAGE, approveMessage);
            SessionUtil.setupInclude(this, req, resp, ADD_TEACHER_JSP_URL);
        } catch (ServiceException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
        }
    }
}
