package by.academy.jee.web.servlet.admin;

import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Student;
import by.academy.jee.web.servlet.AbstractServlet;
import by.academy.jee.web.util.SessionUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.web.constant.Constant.ADD_STUDENT_JSP_URL;
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.STUDENT_IS_SUCCESSFULLY_ADDED;

@WebServlet(value = "/addStudent")
@Slf4j
public class AddStudentServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, ADD_STUDENT_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Student student = service.getStudentFromRequest(req);
            service.checkIsUserNotExist(student.getLogin());
            service.createStudentAfterChecks(student);
            log.info("Student {} is successfully added", student.getLogin());
            req.setAttribute(APPROVE_MESSAGE, STUDENT_IS_SUCCESSFULLY_ADDED);
            SessionUtil.setupInclude(this, req, resp, ADD_STUDENT_JSP_URL);
        } catch (ServiceException | DaoException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, ADD_STUDENT_JSP_URL);
        }
    }
}
