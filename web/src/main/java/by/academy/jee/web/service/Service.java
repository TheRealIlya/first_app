package by.academy.jee.web.service;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.util.Initializer;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.util.SalaryGenerator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static by.academy.jee.web.constant.Constant.ADMIN;
import static by.academy.jee.web.constant.Constant.ADMIN_MENU_JSP_URL;
import static by.academy.jee.web.constant.Constant.NO_SUCH_USER_IN_DATABASE;
import static by.academy.jee.web.constant.Constant.STUDENT;
import static by.academy.jee.web.constant.Constant.STUDENT_MENU_JSP_URL;
import static by.academy.jee.web.constant.Constant.TEACHER;
import static by.academy.jee.web.constant.Constant.TEACHER_MENU_JSP_URL;
import static by.academy.jee.web.constant.Constant.USER_IS_ALREADY_EXIST;

public class Service {

    private Service() {
        //util class
    }

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    public static Teacher getTeacher(HttpServletRequest req) throws ServiceException { //TODO - probably wrap some if/try into private methods

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
                .withSalt(salt)
                .withName(fio)
                .withAge(age)
                .withSalaries(salaries);
    }

    public static void checkIsUserNotExist(String login) throws ServiceException {

        Person user = Initializer.getAdminDao().read(login); // TODO - add check in studentDao
        if (user == null) {
            user = Initializer.getTeacherDao().read(login);
        }
        if (user != null) {
            log.error("Error - attempt to add already existed user {}", login);
            throw new ServiceException(USER_IS_ALREADY_EXIST);
        }
    }

    public static Person getUserIfExist(String login) throws ServiceException {

        Person user = Initializer.getAdminDao().read(login); // TODO - add check in studentDao
        if (user == null) {
            user = Initializer.getTeacherDao().read(login);
            if (user == null) {
                log.error("Error - no user {} in database", login);
                throw new ServiceException(NO_SUCH_USER_IN_DATABASE);
            }
        }
        return user;
    }

    public static String getMenuUrlAfterLogin(String roleString) throws ServiceException {

        switch (roleString) {
            case ADMIN:
                return ADMIN_MENU_JSP_URL;
            case TEACHER:
                return TEACHER_MENU_JSP_URL;
            case STUDENT:
                return STUDENT_MENU_JSP_URL;
            default:
                log.error("Error - incorrect role \"{}\"", roleString);
                throw new ServiceException("Error - role is filled incorrectly. Please contact admin to fix it");
        }
    }

    public static void checkPassword(String attemptedPassword, Person user) throws ServiceException {

        boolean isCorrectPassword = PasswordHasher.authenticate(attemptedPassword, user.getPwd(), user.getSalt());
        if (!isCorrectPassword) {
            log.error("Someone tried to enter as user {} with wrong password", user.getLogin());
            throw new ServiceException("Wrong password");
        }
    }
}
