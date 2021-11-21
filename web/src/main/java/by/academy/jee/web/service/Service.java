package by.academy.jee.web.service;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.PersonDaoFactory;
import by.academy.jee.exception.PersonDaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.util.Initializer;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.util.SalaryGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static by.academy.jee.web.constant.Constant.ADMIN;
import static by.academy.jee.web.constant.Constant.ADMIN_MENU_JSP_URL;
import static by.academy.jee.web.constant.Constant.AGE;
import static by.academy.jee.web.constant.Constant.ERROR_AGE_AND_SALARIES_MUST_BE_NUMBERS;
import static by.academy.jee.web.constant.Constant.ERROR_INCORRECT_ROLE;
import static by.academy.jee.web.constant.Constant.ERROR_WRONG_MONTHS_FORMAT;
import static by.academy.jee.web.constant.Constant.ERROR_WRONG_MONTHS_INPUT;
import static by.academy.jee.web.constant.Constant.ERROR_WRONG_NUMBERS_FORMAT;
import static by.academy.jee.web.constant.Constant.ERROR_WRONG_PASSWORD;
import static by.academy.jee.web.constant.Constant.ERROR_WRONG_SALARIES_INPUT;
import static by.academy.jee.web.constant.Constant.ERROR_WRONG_SALARIES_LOGIC;
import static by.academy.jee.web.constant.Constant.LOGIN;
import static by.academy.jee.web.constant.Constant.MAX_SALARY;
import static by.academy.jee.web.constant.Constant.MIN_SALARY;
import static by.academy.jee.web.constant.Constant.NO_SUCH_USER_IN_DATABASE;
import static by.academy.jee.web.constant.Constant.PASSWORD;
import static by.academy.jee.web.constant.Constant.STUDENT;
import static by.academy.jee.web.constant.Constant.STUDENT_MENU_JSP_URL;
import static by.academy.jee.web.constant.Constant.TEACHER;
import static by.academy.jee.web.constant.Constant.TEACHER_MENU_JSP_URL;
import static by.academy.jee.web.constant.Constant.USER_IS_ALREADY_EXIST;
import static by.academy.jee.web.constant.Constant.USER_NAME;

public class Service {

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    private static PersonDao<Admin> adminDao = PersonDaoFactory.getPersonDao(Role.ADMIN);
    private static PersonDao<Teacher> teacherDao = PersonDaoFactory.getPersonDao(Role.TEACHER);

    private Service() {
        //util class
    }

    static {
        Initializer.initDatabase();
    }

    public static Teacher getTeacherFromRequest(HttpServletRequest req) throws ServiceException { //TODO - probably wrap some if/try into private methods

        String userName = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String fio = req.getParameter(USER_NAME);
        String ageString = req.getParameter(AGE);
        String minSalaryString = req.getParameter(MIN_SALARY);
        String maxSalaryString = req.getParameter(MAX_SALARY);
        byte[] salt = PasswordHasher.generateSalt();
        byte[] encryptedPassword = PasswordHasher.getEncryptedPassword(password, salt);
        int age;
        double minSalary, maxSalary;
        try {
            age = Integer.parseInt(ageString);
            minSalary = Double.parseDouble(minSalaryString);
            maxSalary = Double.parseDouble(maxSalaryString);
        } catch (NumberFormatException e) {
            log.error(ERROR_WRONG_NUMBERS_FORMAT);
            throw new ServiceException(ERROR_AGE_AND_SALARIES_MUST_BE_NUMBERS);
        }
        if (minSalary <= 0 || maxSalary <= 0 || minSalary > maxSalary) {
            log.error(ERROR_WRONG_SALARIES_INPUT);
            throw new ServiceException(ERROR_WRONG_SALARIES_LOGIC);
        }
        Map<Integer, Double> salaries = SalaryGenerator.generate(minSalary, maxSalary);
        return new Teacher()
                .withLogin(userName)
                .withPwd(encryptedPassword)
                .withSalt(salt)
                .withName(fio)
                .withAge(age)
                .withSalaries(salaries)
                .withRole(Role.TEACHER);
    }

    public static void checkIsUserNotExist(String login) throws ServiceException {

        try { //TODO - add check in studentDao
            adminDao.read(login);
        } catch (PersonDaoException e) {
            try {
                teacherDao.read(login);
            } catch (PersonDaoException f) {
                return;
            }
        }
        log.error("Error - attempt to add already existed user {}", login);
        throw new ServiceException(USER_IS_ALREADY_EXIST);
    }

    public static Person getUserIfExist(String login) throws ServiceException {

        Person user; // TODO - add check in studentDao
        try {
            user = adminDao.read(login);
        } catch (PersonDaoException e) {
            try {
                user = teacherDao.read(login);
            } catch (PersonDaoException f) {
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
                throw new ServiceException(ERROR_INCORRECT_ROLE);
        }
    }

    public static void checkPassword(String attemptedPassword, Person user) throws ServiceException {

        boolean isCorrectPassword = PasswordHasher.authenticate(attemptedPassword, user.getPwd(), user.getSalt());
        if (!isCorrectPassword) {
            log.error("Someone tried to enter as user {} with wrong password", user.getLogin());
            throw new ServiceException(ERROR_WRONG_PASSWORD);
        }
    }

    public static String getAverageSalaryByMonths(Teacher teacher, String firstMonthString, String lastMonthString)
            throws ServiceException {

        List<Integer> months = new ArrayList<>();
        try {
            int firstMonth = Integer.parseInt(firstMonthString);
            int lastMonth = Integer.parseInt(lastMonthString);
            if (firstMonth <= 0 || lastMonth > 12 || firstMonth > lastMonth) {
                log.error(ERROR_WRONG_MONTHS_INPUT);
                throw new ServiceException(ERROR_WRONG_MONTHS_INPUT);
            }
            for (int i = firstMonth; i <= lastMonth; i++) {
                months.add(i);
            }
            return calculateAverageSalaryByMonths(teacher, months);
        } catch (NumberFormatException e) {
            log.error(ERROR_WRONG_MONTHS_FORMAT);
            throw new ServiceException(ERROR_WRONG_MONTHS_FORMAT);
        }
    }

    public static void checkIsNotATeacher(Person person) throws ServiceException {

        if (!Role.TEACHER.equals(person.getRole())) {
            log.error("Error - user {} is not a teacher", person.getLogin());
            throw new ServiceException("Error - this user isn't a teacher");
        }
    }

    private static String calculateAverageSalaryByMonths(Teacher teacher, List<Integer> months) {

        double sum = 0;
        int divider = 0;
        Map<Integer, Double> salaries = teacher.getSalaries();
        for (Integer month : months) {
            if (salaries.containsKey(month)) {
                sum += salaries.get(month);
                divider++;
            }
        }
        double averageSalary = sum / divider;
        String result = String.format("%.2f", averageSalary).replace(',', '.');
        return result;
    }
}
