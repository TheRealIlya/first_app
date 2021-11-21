package by.academy.jee.web.servlet.admin;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.PersonDaoFactory;
import by.academy.jee.exception.PersonDaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.web.service.Service;
import by.academy.jee.web.util.SessionUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.web.constant.Constant.ADD_STUDENT_JSP_URL;
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.STUDENT_IS_SUCCESSFULLY_ADDED;

@WebServlet(value = "/addStudent")
@Slf4j
public class AddStudentServlet extends HttpServlet {

    private static PersonDao<Student> studentDao = PersonDaoFactory.getPersonDao(Role.STUDENT);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, ADD_STUDENT_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Student student = Service.getStudentFromRequest(req);
            Service.checkIsUserNotExist(student.getLogin());
            studentDao.create(student);
            log.info("Student {} is successfully added", student.getLogin());
            req.setAttribute(APPROVE_MESSAGE, STUDENT_IS_SUCCESSFULLY_ADDED);
            SessionUtil.setupInclude(this, req, resp, ADD_STUDENT_JSP_URL);
        } catch (ServiceException | PersonDaoException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, ADD_STUDENT_JSP_URL);
        }
    }
}
