package by.academy.jee.web.service;

import by.academy.jee.dao.group.GroupDao;
import by.academy.jee.dao.group.GroupDaoFactory;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.PersonDaoFactory;
import by.academy.jee.dao.theme.ThemeDao;
import by.academy.jee.dao.theme.ThemeDaoFactory;
import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.MyNoResultException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.grade.Grade;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.PersonContext;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.model.theme.Theme;
import by.academy.jee.util.Initializer;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.util.SalaryGenerator;
import by.academy.jee.web.util.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static by.academy.jee.web.constant.Constant.ADMIN;
import static by.academy.jee.web.constant.Constant.ADMIN_MENU_JSP_URL;
import static by.academy.jee.web.constant.Constant.AGE;
import static by.academy.jee.web.constant.Constant.ERROR_WRONG_GRADE_FORMAT;
import static by.academy.jee.web.constant.Constant.SALARIES_MUST_BE_NUMBERS;
import static by.academy.jee.web.constant.Constant.ERROR_AGE_MUST_BE_A_NUMBER;
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
    private static PersonDao<Student> studentDao = PersonDaoFactory.getPersonDao(Role.STUDENT);
    private static GroupDao groupDao = GroupDaoFactory.getGroupDao();
    private static ThemeDao themeDao = ThemeDaoFactory.getThemeDao();

    private Service() {
        //util class
    }

    static {
        Initializer.initDatabase();
    }

    public static Student getStudentFromRequest(HttpServletRequest req) throws ServiceException {

        PersonContext context = getPersonContextFromRequest(req);
        return new Student()
                .withLogin(context.getLogin())
                .withPwd(context.getPwd())
                .withSalt(context.getSalt())
                .withName(context.getName())
                .withAge(context.getAge())
                .withRole(Role.STUDENT);
    }

    public static Teacher getTeacherFromRequest(HttpServletRequest req) throws ServiceException {

        PersonContext context = getPersonContextFromRequest(req);
        String minSalaryString = req.getParameter(MIN_SALARY);
        String maxSalaryString = req.getParameter(MAX_SALARY);
        double minSalary, maxSalary;
        try {
            minSalary = Double.parseDouble(minSalaryString);
            maxSalary = Double.parseDouble(maxSalaryString);
        } catch (NumberFormatException e) {
            log.error(ERROR_WRONG_NUMBERS_FORMAT);
            throw new ServiceException(SALARIES_MUST_BE_NUMBERS);
        }
        if (minSalary <= 0 || maxSalary <= 0 || minSalary > maxSalary) {
            log.error(ERROR_WRONG_SALARIES_INPUT);
            throw new ServiceException(ERROR_WRONG_SALARIES_LOGIC);
        }
        Map<Integer, Double> salaries = SalaryGenerator.generate(minSalary, maxSalary);
        return new Teacher()
                .withLogin(context.getLogin())
                .withPwd(context.getPwd())
                .withSalt(context.getSalt())
                .withName(context.getName())
                .withAge(context.getAge())
                .withSalaries(salaries)
                .withRole(Role.TEACHER);
    }

    public static void checkIsUserNotExist(String login) throws ServiceException {

        try {
            adminDao.read(login);
        } catch (DaoException e) {
            try {
                teacherDao.read(login);
            } catch (DaoException f) {
                try {
                    studentDao.read(login);
                } catch (DaoException g) {
                    return;
                }
            }
        }
        log.error("Error - attempt to add already existed user {}", login);
        throw new ServiceException(USER_IS_ALREADY_EXIST);
    }

    public static Person getUserIfExist(String login) throws ServiceException {

        Person user;
        try {
            user = adminDao.read(login);
        } catch (DaoException e) {
            try {
                user = teacherDao.read(login);
            } catch (DaoException f) {
                try {
                    user = studentDao.read(login);
                } catch (DaoException g) {
                    log.error("Error - no user {} in database", login);
                    throw new ServiceException(NO_SUCH_USER_IN_DATABASE);
                }
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

    public static Group getGroupByTeacher(Teacher teacher) throws ServiceException {

        try {
            return groupDao.read(teacher);
        } catch (MyNoResultException e) {
            log.error(e.getMessage());
            throw new ServiceException("Error - you don't have a group");
        } catch (DaoException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public static void createGrade(String studentLogin, Group group, String themeString, String gradeString)
            throws ServiceException {

        try {
            int gradeValue = Integer.parseInt(gradeString);
            if (gradeValue < 1 || gradeValue > 10) {
                throw new NumberFormatException();
            }
            Student student = studentDao.read(studentLogin);
            if (!group.getStudents().contains(student)) {
                throw new ServiceException("Error - no such student in current group");
            }
            Theme theme = themeDao.read(themeString);
            if (!group.getThemes().contains(theme)) {
                throw new ServiceException("Error - this group doesn't contain such theme");
            }
            Grade grade = new Grade()
                    .withValue(gradeValue)
                    .withStudent(student)
                    .withGroup(group)
                    .withTheme(theme);
            student.getGrades().add(grade);
            studentDao.update(student);
        } catch (NumberFormatException e) {
            log.error(ERROR_WRONG_GRADE_FORMAT);
            throw new ServiceException(ERROR_WRONG_GRADE_FORMAT);
        } catch (MyNoResultException | DaoException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public static void changeGroup(Group oldGroup, String newGroupTitle, Teacher teacher) throws ServiceException {

        try {
            if (newGroupTitle == null || newGroupTitle.equals("")) {
                if (oldGroup == null) {
                    throw new ServiceException("Error - you already don't have a group");
                }
                setTeacherForGroup(oldGroup, null);
                return;
            }
            Group newGroup = groupDao.read(newGroupTitle);
            if (newGroup.getTeacher() != null) {
                throw new ServiceException("Error - this group already has a teacher");
            }
            if (oldGroup != null) {
                setTeacherForGroup(oldGroup, null);
            }
            setTeacherForGroup(newGroup, teacher);
        } catch (MyNoResultException | DaoException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    private static void setTeacherForGroup(Group group, Teacher teacher) throws DaoException {

        group.setTeacher(teacher);
        groupDao.update(group);
    }

    private static PersonContext getPersonContextFromRequest(HttpServletRequest req) throws ServiceException {

        String userName = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String fio = req.getParameter(USER_NAME);
        String ageString = req.getParameter(AGE);
        byte[] salt = PasswordHasher.generateSalt();
        byte[] encryptedPassword = PasswordHasher.getEncryptedPassword(password, salt);
        int age;
        try {
            age = Integer.parseInt(ageString);
        } catch (NumberFormatException e) {
            log.error(ERROR_WRONG_NUMBERS_FORMAT);
            throw new ServiceException(ERROR_AGE_MUST_BE_A_NUMBER);
        }
        return PersonContext.builder()
                .login(userName)
                .pwd(encryptedPassword)
                .salt(salt)
                .name(fio)
                .age(age)
                .build();
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
