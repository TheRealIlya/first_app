package by.academy.jee.web.servlet;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.service.Service;
import by.academy.jee.web.util.SessionUtil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static by.academy.jee.web.constant.Constant.AVERAGE_SALARY_CALCULATED;
import static by.academy.jee.web.constant.Constant.AVG_SALARY_JSP_URL;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.FIRST_MONTH;
import static by.academy.jee.web.constant.Constant.LAST_MONTH;
import static by.academy.jee.web.constant.Constant.LOGIN;
import static by.academy.jee.web.constant.Constant.RESULT;

@WebServlet(value = "/avgSalary")
public class AverageSalaryServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(AverageSalaryServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, AVG_SALARY_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String login = req.getParameter(LOGIN);
            Person person = Service.getUserIfExist(login);
            Service.checkIsNotATeacher(person);
            Teacher teacher = (Teacher) person;
            String firstMonth = req.getParameter(FIRST_MONTH);
            String lastMonth = req.getParameter(LAST_MONTH);
            String averageSalaryString = Service.getAverageSalaryByMonths(teacher, firstMonth, lastMonth);
            log.info(AVERAGE_SALARY_CALCULATED);
            String result = "Average salary: " + averageSalaryString;
            req.setAttribute(RESULT, result);
            SessionUtil.setupInclude(this, req, resp, AVG_SALARY_JSP_URL);
        } catch (ServiceException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            SessionUtil.setupForward(this, req, resp, AVG_SALARY_JSP_URL);
        }
    }
}
