package by.academy.jee.web.service;

import by.academy.jee.dao.RepositoryType;
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
import by.academy.jee.util.DataBaseUtil;
import by.academy.jee.util.Initializer;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.util.SalaryGenerator;
import by.academy.jee.util.ThreadLocalForEntityManager;
import by.academy.jee.util.ThreadLocalForRepositoryType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class Service {

    private static final RepositoryType TYPE;
    private static final ThreadLocalForRepositoryType repositoryTypeHelper = ThreadLocalForRepositoryType.getInstance();
    private final PersonDao<Admin> adminDao = PersonDaoFactory.getPersonDao(Role.ADMIN);
    private final PersonDao<Teacher> teacherDao = PersonDaoFactory.getPersonDao(Role.TEACHER);
    private final PersonDao<Student> studentDao = PersonDaoFactory.getPersonDao(Role.STUDENT);
    private final GroupDao groupDao = GroupDaoFactory.getGroupDao();
    private final ThemeDao themeDao = ThemeDaoFactory.getThemeDao();
    private final ThreadLocalForEntityManager emHelper = ThreadLocalForEntityManager.getInstance();

    private static volatile Service instance;

    static {
        Initializer.initDatabase();
        TYPE = repositoryTypeHelper.get();
    }

    private Service() {
        //singleton
    }

    public static Service getInstance() {

        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Service();
                }
            }
        }
        return instance;
    }

    public Student getStudentFromRequest(HttpServletRequest req) throws ServiceException {

        PersonContext context = getPersonContextFromRequest(req);
        return new Student()
                .withLogin(context.getLogin())
                .withPwd(context.getPwd())
                .withSalt(context.getSalt())
                .withName(context.getName())
                .withAge(context.getAge())
                .withRole(Role.STUDENT);
    }

    public void createStudentAfterChecks(Student student) throws ServiceException {

        try {
            beginTransaction();
            studentDao.create(student);
        } catch (DaoException e) {
            DataBaseUtil.rollBack(emHelper.get());
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            closeTransaction();
        }
    }

    public void createTeacherAfterChecks(Teacher teacher) throws ServiceException {

        try {
            beginTransaction();
            teacherDao.create(teacher);
        } catch (DaoException e) {
            DataBaseUtil.rollBack(emHelper.get());
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            closeTransaction();
        }
    }

    public Teacher getTeacherFromRequest(HttpServletRequest req) throws ServiceException {

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

    public void checkIsUserNotExist(String login) throws ServiceException {

        beginTransaction();
        try {
            adminDao.read(login);
        } catch (DaoException e) {
            try {
                teacherDao.read(login);
            } catch (DaoException f) {
                try {
                    studentDao.read(login);
                } catch (DaoException g) {
                    closeTransaction();
                    return;
                }
            }
        }
        DataBaseUtil.rollBack(emHelper.get());
        closeTransaction();
        log.error("Error - attempt to add already existed user {}", login);
        throw new ServiceException(USER_IS_ALREADY_EXIST);
    }

    public Person getUserIfExist(String login) throws ServiceException {

        Person user;
        beginTransaction();
        try {
            user = adminDao.read(login);
        } catch (DaoException e) {
            try {
                user = teacherDao.read(login);
            } catch (DaoException f) {
                try {
                    user = studentDao.read(login);
                } catch (DaoException g) {
                    DataBaseUtil.rollBack(emHelper.get());
                    log.error("Error - no user {} in database", login);
                    throw new ServiceException(NO_SUCH_USER_IN_DATABASE);
                }
            }
        } finally {
            closeTransaction();
        }
        return user;
    }

    public String getMenuUrlAfterLogin(String roleString) throws ServiceException {

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

    public void checkPassword(String attemptedPassword, Person user) throws ServiceException {

        boolean isCorrectPassword = PasswordHasher.authenticate(attemptedPassword, user.getPwd(), user.getSalt());
        if (!isCorrectPassword) {
            log.error("Someone tried to enter as user {} with wrong password", user.getLogin());
            throw new ServiceException(ERROR_WRONG_PASSWORD);
        }
    }

    public String getAverageSalaryByMonths(Teacher teacher, String firstMonthString, String lastMonthString)
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

    public void checkIsNotATeacher(Person person) throws ServiceException {

        if (!Role.TEACHER.equals(person.getRole())) {
            log.error("Error - user {} is not a teacher", person.getLogin());
            throw new ServiceException("Error - this user isn't a teacher");
        }
    }

    public Group getGroupByTeacher(Teacher teacher) throws ServiceException {

        beginTransaction();
        try {
            return groupDao.read(teacher);
        } catch (MyNoResultException e) {
            DataBaseUtil.rollBack(emHelper.get());
            log.error(e.getMessage());
            throw new ServiceException("Error - you don't have a group");
        } catch (DaoException e) {
            DataBaseUtil.rollBack(emHelper.get());
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            closeTransaction();
        }
    }

    public void createGrade(String studentLogin, Group group, String themeString, String gradeString)
            throws ServiceException {

        try {
            int gradeValue = Integer.parseInt(gradeString);
            if (gradeValue < 1 || gradeValue > 10) {
                throw new NumberFormatException();
            }
            beginTransaction();
            Student student = studentDao.read(studentLogin);
            if (!group.getStudents().contains(student)) {
                DataBaseUtil.rollBack(emHelper.get());
                throw new ServiceException("Error - no such student in current group");
            }
            Theme theme = themeDao.read(themeString);
            if (!group.getThemes().contains(theme)) {
                DataBaseUtil.rollBack(emHelper.get());
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
            DataBaseUtil.rollBack(emHelper.get());
            log.error(ERROR_WRONG_GRADE_FORMAT);
            throw new ServiceException(ERROR_WRONG_GRADE_FORMAT);
        } catch (MyNoResultException | DaoException e) {
            DataBaseUtil.rollBack(emHelper.get());
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            closeTransaction();
        }
    }

    public void changeGroup(Group oldGroup, String newGroupTitle, Teacher teacher) throws ServiceException {

        try {
            if (newGroupTitle == null || newGroupTitle.equals("")) {
                if (oldGroup == null) {
                    throw new ServiceException("Error - you already don't have a group");
                }
                setTeacherForGroup(oldGroup, null);
                return;
            }
            beginTransaction();
            Group newGroup = groupDao.read(newGroupTitle);
            if (newGroup.getTeacher() != null) {
                DataBaseUtil.rollBack(emHelper.get());
                throw new ServiceException("Error - this group already has a teacher");
            }
            if (oldGroup != null) {
                setTeacherForGroup(oldGroup, null);
            }
            setTeacherForGroup(newGroup, teacher);
        } catch (MyNoResultException | DaoException e) {
            DataBaseUtil.rollBack(emHelper.get());
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            closeTransaction();
        }
    }

    private void setTeacherForGroup(Group group, Teacher teacher) throws DaoException {

        group.setTeacher(teacher);
        groupDao.update(group);
    }

    private PersonContext getPersonContextFromRequest(HttpServletRequest req) throws ServiceException {

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

    private String calculateAverageSalaryByMonths(Teacher teacher, List<Integer> months) {

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

    private void beginTransaction() {

        switch (TYPE) {
            case MEMORY:

            case POSTGRES:

            case JPA:
            default:
                emHelper.set();
                emHelper.get().getTransaction().begin();
        }
    }

    private void closeTransaction() {

        switch (TYPE) {
            case MEMORY:

            case POSTGRES:

            case JPA:
            default:
                DataBaseUtil.closeEntityManager(emHelper.get());
                DataBaseUtil.finallyCloseEntityManager(emHelper.get());
                emHelper.remove();
        }
    }
}
