package by.academy.jee.web.service;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.util.Initializer;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.util.SalaryGenerator;
import by.academy.jee.web.util.SessionUtil;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static by.academy.jee.web.constant.Constant.ADD_TEACHER_JSP_URL;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.USER_IS_ALREADY_EXIST;

public class Service {

    private Service() {
        //util class
    }

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    public Teacher getTeacher(HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ServiceException {

        String userName = req.getParameter("login");
        String password = req.getParameter("password");
        String fio = req.getParameter("userName");
        String ageString = req.getParameter("age");
        String minSalaryString = req.getParameter("minSalary");
        String maxSalaryString = req.getParameter("maxSalary");
        byte[] salt = PasswordHasher.generateSalt();
        byte[] encryptedPassword = PasswordHasher.getEncryptedPassword(password, salt);
        int age;
        double minSalary, maxSalary;
        try {
            age = Integer.parseInt(ageString);
            minSalary = Double.parseDouble(minSalaryString);
            maxSalary = Double.parseDouble(maxSalaryString);
        } catch (NumberFormatException e) {
            log.error("Error - wrong numbers format");
            throw new ServiceException("Error - age and salaries must be numbers!");
        }
        if (minSalary <= 0 || maxSalary <= 0 || minSalary > maxSalary) {
            log.error("Error - wrong salaries input");
            throw new ServiceException("Error - salaries can't be < 0 and minimal salary must be " +
                    "lower than maximal salary!");
        }
        Map<Integer, Double> salaries = SalaryGenerator.generate(minSalary, maxSalary);
        return new Teacher()
                .withLogin(userName)
                .withPwd(encryptedPassword)
                .withName(fio)
                .withAge(age)
                .withSalaries(salaries);
    }
}
