package by.academy.jee.web.servlet;

import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.util.Initializer;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.util.SalaryGenerator;
import by.academy.jee.web.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static by.academy.jee.web.constant.Constant.*;

@WebServlet(value = "/addTeacher")
public class AddTeacherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("login");
        String password = req.getParameter("password");
        String fio = req.getParameter("userName");
        String ageString = req.getParameter("age");
        String minSalaryString = req.getParameter("minSalary");
        String maxSalaryString = req.getParameter("maxSalary");
        Person user = Initializer.adminDao.read(userName); // TODO - add check in studentDao
        if (user == null) {
            user = Initializer.teacherDao.read(userName);
        }
        if (user != null) {
            req.setAttribute(ERROR_MESSAGE, USER_IS_ALREADY_EXIST);
            SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
            return;
        }
        int age;
        double minSalary, maxSalary;
        try {
            age = Integer.parseInt(ageString);
            minSalary = Double.parseDouble(minSalaryString);
            maxSalary = Double.parseDouble(maxSalaryString);
        } catch (NumberFormatException e) {
            String errorMessage = "Error - age and salaries must be numbers!";
            req.setAttribute(ERROR_MESSAGE, errorMessage);
            SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
            return;
        }
        if (minSalary <= 0 || maxSalary <= 0 || minSalary > maxSalary) {
            String errorMessage = "Error - salaries can't be < 0 and minimal salary must be lower than maximal salary!";
            req.setAttribute(ERROR_MESSAGE, errorMessage);
            SessionUtil.setupForward(this, req, resp, ADD_TEACHER_JSP_URL);
            return;
        }
        byte[] salt = PasswordHasher.generateSalt();
        byte[] hashPwd = PasswordHasher.getEncryptedPassword(password, salt);
        Map<Integer, Double> salaries = SalaryGenerator.generate(minSalary, maxSalary);
        Teacher teacher = new Teacher(userName, hashPwd, salt, fio, age, salaries);
        Initializer.teacherDao.create(teacher);
        String approveMessage = "Teacher is successfully added!";
        req.setAttribute(APPROVE_MESSAGE, approveMessage);
        SessionUtil.setupInclude(this, req, resp, ADD_TEACHER_JSP_URL);
    }
}
