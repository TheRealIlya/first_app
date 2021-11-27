package by.academy.jee.web.servlet.teacher;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
import by.academy.jee.web.service.Service;
import by.academy.jee.web.util.SessionUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.CREATE_GRADE_JSP_URL;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;

@Slf4j
@WebServlet(value = "/createGrade")
public class CreateGradeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, CREATE_GRADE_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String studentLogin = req.getParameter("studentLogin");
            Group group = SessionUtil.getSessionGroup(req);
            String themeString = req.getParameter("themeString");
            String gradeString = req.getParameter("gradeString");
            Service.createGrade(studentLogin, group, themeString, gradeString);
            log.info("Grade has been added");
            req.setAttribute(APPROVE_MESSAGE, "Grade has been added");
            SessionUtil.setupInclude(this, req, resp, CREATE_GRADE_JSP_URL);
        } catch (ServiceException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, CREATE_GRADE_JSP_URL);
        }

    }
}
