package by.academy.jee.web.service;

import by.academy.jee.dao.grade.GradeDao;
import by.academy.jee.dao.group.GroupDao;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.theme.ThemeDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.grade.Grade;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.PersonDto;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.model.theme.Theme;
import by.academy.jee.util.DataBaseUtil;
import by.academy.jee.util.PasswordHasher;
import by.academy.jee.util.SalaryGenerator;
import by.academy.jee.util.ThreadLocalForEntityManager;
import by.academy.jee.web.aspect.service.ServiceExceptionHandler;
import by.academy.jee.web.aspect.service.ServiceTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import static by.academy.jee.constant.CommonConstant.ADMIN;
import static by.academy.jee.constant.CommonConstant.AGE;
import static by.academy.jee.constant.CommonConstant.LOGIN;
import static by.academy.jee.constant.CommonConstant.PASSWORD;
import static by.academy.jee.constant.CommonConstant.REPOSITORY_PROPERTIES;
import static by.academy.jee.constant.CommonConstant.STUDENT;
import static by.academy.jee.constant.CommonConstant.TEACHER;
import static by.academy.jee.constant.CommonConstant.USER_NAME;
import static by.academy.jee.constant.ControllerConstant.ADMIN_MENU_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.STUDENT_MENU_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.TEACHER_MENU_JSP_URL;
import static by.academy.jee.constant.ExceptionConstant.ERROR_AGE_MUST_BE_A_NUMBER;
import static by.academy.jee.constant.ExceptionConstant.ERROR_GROUP_ALREADY_EXIST;
import static by.academy.jee.constant.ExceptionConstant.ERROR_GROUP_ALREADY_HAS_A_TEACHER;
import static by.academy.jee.constant.ExceptionConstant.ERROR_NO_STUDENT_IN_GROUP;
import static by.academy.jee.constant.ExceptionConstant.ERROR_NO_THEME_IN_GROUP;
import static by.academy.jee.constant.ExceptionConstant.ERROR_SALARIES_MUST_BE_NUMBERS;
import static by.academy.jee.constant.ExceptionConstant.ERROR_THEME_ALREADY_EXIST;
import static by.academy.jee.constant.ExceptionConstant.ERROR_WRONG_GRADE_FORMAT;
import static by.academy.jee.constant.ExceptionConstant.ERROR_WRONG_MONTHS_FORMAT;
import static by.academy.jee.constant.ExceptionConstant.ERROR_WRONG_MONTHS_INPUT;
import static by.academy.jee.constant.ExceptionConstant.ERROR_WRONG_NUMBERS_FORMAT;
import static by.academy.jee.constant.ExceptionConstant.ERROR_WRONG_PASSWORD;
import static by.academy.jee.constant.ExceptionConstant.ERROR_WRONG_SALARIES_INPUT;
import static by.academy.jee.constant.ExceptionConstant.ERROR_WRONG_SALARIES_LOGIC;
import static by.academy.jee.constant.ExceptionConstant.USER_IS_ALREADY_EXIST;
import static by.academy.jee.constant.ServiceConstant.ADMIN_PREFIX;
import static by.academy.jee.constant.ServiceConstant.GRADE_PREFIX;
import static by.academy.jee.constant.ServiceConstant.GROUP_PREFIX;
import static by.academy.jee.constant.ServiceConstant.MAX_SALARY;
import static by.academy.jee.constant.ServiceConstant.MIN_SALARY;
import static by.academy.jee.constant.ServiceConstant.NO_SUCH_USER_IN_DATABASE;
import static by.academy.jee.constant.ServiceConstant.STUDENT_PREFIX;
import static by.academy.jee.constant.ServiceConstant.TEACHER_PREFIX;
import static by.academy.jee.constant.ServiceConstant.THEME_PREFIX;

@Slf4j
@org.springframework.stereotype.Service
@Transactional
@PropertySource(REPOSITORY_PROPERTIES)
public class Service {

    private final String type;

    private PersonDao<Admin> adminDao;
    private PersonDao<Teacher> teacherDao;
    private PersonDao<Student> studentDao;
    @Autowired
    private Map<String, PersonDao> personDaoMap;

    private GroupDao groupDao;
    @Autowired
    private Map<String, GroupDao> groupDaoMap;

    private ThemeDao themeDao;
    @Autowired
    private Map<String, ThemeDao> themeDaoMap;

    private GradeDao gradeDao;
    @Autowired
    private Map<String, GradeDao> gradeDaoMap;

    private final ThreadLocalForEntityManager emHelper = ThreadLocalForEntityManager.getInstance();

    public Service(@Value("${repository.type}") String type) {
        this.type = StringUtils.capitalize(type);
    }

    @PostConstruct
    private void init() {

        String adminDaoTitle = ADMIN_PREFIX + type;
        String teacherDaoTitle = TEACHER_PREFIX + type;
        String studentDaoTitle = STUDENT_PREFIX + type;
        String groupDaoTitle = GROUP_PREFIX + type;
        String themeDaoTitle = THEME_PREFIX + type;
        String gradeDaoTitle = GRADE_PREFIX + type;
        adminDao = personDaoMap.get(adminDaoTitle);
        teacherDao = personDaoMap.get(teacherDaoTitle);
        studentDao = personDaoMap.get(studentDaoTitle);
        groupDao = groupDaoMap.get(groupDaoTitle);
        themeDao = themeDaoMap.get(themeDaoTitle);
        gradeDao = gradeDaoMap.get(gradeDaoTitle);
    }

    public Student getStudentFromRequestWithoutId(HttpServletRequest req) throws ServiceException {

        PersonDto personDto = getPersonDtoFromRequest(req);
        personDto.setRole(Role.STUDENT);
        return (Student) getPersonFromDto(personDto);
    }

    public Person getPersonFromDto(PersonDto personDto) {

        Map<Role, Person> personMap = Map.of(Role.ADMIN, new Admin(),
                Role.TEACHER, new Teacher().withSalaries(personDto.getSalaries()),
                Role.STUDENT, new Student());
        Person person = personMap.get(personDto.getRole());
        if (personDto.getId() != null) {
            person.setId(personDto.getId());
        }
        person.setLogin(personDto.getLogin());
        person.setPwd(personDto.getPwd());
        person.setSalt(personDto.getSalt());
        person.setName(personDto.getName());
        person.setAge(personDto.getAge());
        return person;
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Person createPerson(Person person) throws ServiceException {

        checkIsUserNotExist(person.getLogin());
        switch (person.getRole()) {
            case ADMIN:
                return adminDao.create((Admin) person);
            case TEACHER:
                return teacherDao.create((Teacher) person);
            case STUDENT:
            default:
                return studentDao.create((Student) person);
        }
    }

    public Teacher getTeacherFromRequest(HttpServletRequest req) throws ServiceException {

        PersonDto personDto = getPersonDtoFromRequest(req);
        personDto.setRole(Role.TEACHER);
        String minSalaryString = req.getParameter(MIN_SALARY);
        String maxSalaryString = req.getParameter(MAX_SALARY);
        double minSalary, maxSalary;
        try {
            minSalary = Double.parseDouble(minSalaryString);
            maxSalary = Double.parseDouble(maxSalaryString);
        } catch (NumberFormatException e) {
            log.error(ERROR_WRONG_NUMBERS_FORMAT);
            throw new ServiceException(ERROR_SALARIES_MUST_BE_NUMBERS);
        }
        if (minSalary <= 0 || maxSalary <= 0 || minSalary > maxSalary) {
            log.error(ERROR_WRONG_SALARIES_INPUT);
            throw new ServiceException(ERROR_WRONG_SALARIES_LOGIC);
        }
        Map<Integer, Double> salaries = SalaryGenerator.generate(minSalary, maxSalary);
        Teacher teacher = (Teacher) getPersonFromDto(personDto);
        teacher.setSalaries(salaries);
        return teacher;
    }

    @ServiceTransaction
    public Person getUserIfExist(String login) throws ServiceException {

        Person user;
        try {
            user = studentDao.read(login);
        } catch (DaoException e) {
            try {
                user = teacherDao.read(login);
            } catch (DaoException f) {
                try {
                    user = adminDao.read(login);
                } catch (DaoException g) {
                    log.error("Error - no user {} in database", login);
                    throw new ServiceException(NO_SUCH_USER_IN_DATABASE);
                }
            }
        }
        return user;
    }

    public String getMenuUrlAfterLogin(String roleString) throws ServiceException {

        Map<String, String> urlMap = Map.of(ADMIN, ADMIN_MENU_JSP_URL,
                TEACHER, TEACHER_MENU_JSP_URL,
                STUDENT, STUDENT_MENU_JSP_URL);
        return urlMap.get(roleString);
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

    @ServiceExceptionHandler
    @ServiceTransaction
    public Group getGroupByTeacher(Teacher teacher) throws ServiceException {
        return groupDao.read(teacher);
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public void createGrade(String studentLogin, Group group, String themeString, String gradeString)
            throws ServiceException {

        try {
            int gradeValue = Integer.parseInt(gradeString);
            if (gradeValue < 1 || gradeValue > 10) {
                throw new NumberFormatException();
            }
            Student student = studentDao.read(studentLogin);
            if (!group.getStudents().contains(student)) {
                DataBaseUtil.rollBack(emHelper.get());
                throw new ServiceException(ERROR_NO_STUDENT_IN_GROUP);
            }
            Theme theme = themeDao.read(themeString);
            if (!group.getThemes().contains(theme)) {
                DataBaseUtil.rollBack(emHelper.get());
                throw new ServiceException(ERROR_NO_THEME_IN_GROUP);
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
        }
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public void changeGroup(Group oldGroup, String newGroupTitle, Teacher teacher) throws ServiceException {

        if (newGroupTitle == null || newGroupTitle.equals("")) {
            if (oldGroup == null) {
                DataBaseUtil.rollBack(emHelper.get());
                throw new ServiceException("Error - you already don't have a group");
            }
            setTeacherForGroup(oldGroup, null);
            return;
        }
        Group newGroup = groupDao.read(newGroupTitle);
        if (newGroup.getTeacher() != null) {
            DataBaseUtil.rollBack(emHelper.get());
            throw new ServiceException(ERROR_GROUP_ALREADY_HAS_A_TEACHER);
        }
        if (oldGroup != null) {
            setTeacherForGroup(oldGroup, null);
        }
        setTeacherForGroup(newGroup, teacher);
    }

    @ServiceTransaction
    public List<Person> getAllPersons() {

        List<Person> persons = new ArrayList<>(adminDao.readAll());
        List<Teacher> teachers = teacherDao.readAll();
        List<Student> students = studentDao.readAll();
        persons.addAll(teachers);
        persons.addAll(students);
        return persons;
    }

    @ServiceTransaction
    public List<Grade> getAllGrades() {
        return gradeDao.readAll();
    }

    @ServiceTransaction
    @ServiceExceptionHandler
    public Grade createGrade(Grade grade) throws ServiceException {
        return gradeDao.create(grade);
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Grade updateGrade(Grade grade) throws ServiceException {
        return gradeDao.update(grade);
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Grade removeGrade(int id) throws ServiceException {
        Grade grade = gradeDao.read(id);
        gradeDao.delete(grade);
        return grade;
    }

    @ServiceTransaction
    public List<Group> getAllGroups() {
        return groupDao.readAll();
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Group getGroup(String title) throws ServiceException {
        return groupDao.read(title);
    }

    @ServiceTransaction
    public Group createGroup(Group group) throws ServiceException {
        checkIsGroupNotExist(group.getTitle());
        return groupDao.create(group);
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Group updateGroup(Group newGroup) throws ServiceException {
        groupDao.update(newGroup);
        return newGroup;
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Group removeGroup(Group group) throws ServiceException {
        for (Grade grade : group.getGrades()) {
            gradeDao.delete(grade);
        }
        group.setGrades(null);
        for (Student student : group.getStudents()) {
            student.getGroups().remove(group);
            studentDao.update(student);
        }
        groupDao.update(group);
        groupDao.delete(group.getTitle());
        return group;
    }

    @ServiceTransaction
    public List<Theme> getAllThemes() {
        return themeDao.readAll();
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Theme getTheme(String title) throws ServiceException {
        return themeDao.read(title);
    }

    @ServiceTransaction
    public Theme createTheme(Theme theme) throws ServiceException {
        checkIsThemeNotExist(theme.getTitle());
        return themeDao.create(theme);
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Theme updateTheme(Theme newTheme) throws ServiceException {
        themeDao.update(newTheme);
        return newTheme;
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Theme removeTheme(Theme theme) throws ServiceException {

        theme.getGrades()
                .forEach(grade -> gradeDao.delete(grade));
        theme.getGroups()
                .forEach(group -> {
                    group.getThemes().remove(theme);
                    groupDao.update(group);
                });
        themeDao.delete(theme.getTitle());
        return theme;
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Person updatePerson(Person newPerson) throws ServiceException {

        switch (newPerson.getRole()) {
            case ADMIN:
                adminDao.update((Admin) newPerson);
                break;
            case TEACHER:
                teacherDao.update((Teacher) newPerson);
                break;
            case STUDENT:
                studentDao.update((Student) newPerson);
        }
        return newPerson;
    }

    @ServiceExceptionHandler
    @ServiceTransaction
    public Person removeUser(Person person) throws ServiceException {

        switch (person.getRole()) {
            case ADMIN:
                adminDao.delete(person.getLogin());
                break;
            case TEACHER:
                try {
                    Group group = groupDao.read((Teacher) person);
                    group.setTeacher(null);
                    groupDao.update(group);
                } catch (DaoException e) {
                    //ignore
                }
                teacherDao.delete(person.getLogin());
                break;
            case STUDENT:
            default:
                Student student = (Student) person;
                student.getGrades()
                        .forEach(grade -> gradeDao.delete(grade));
                studentDao.delete(person.getLogin());
        }
        return person;
    }

    private void setTeacherForGroup(Group group, Teacher teacher) throws DaoException {
        group.setTeacher(teacher);
        groupDao.update(group);
    }

    private PersonDto getPersonDtoFromRequest(HttpServletRequest req) throws ServiceException {

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
        return PersonDto.builder()
                .login(userName)
                .pwd(encryptedPassword)
                .salt(salt)
                .name(fio)
                .age(age)
                .build();
    }

    private void checkIsGroupNotExist(String title) throws ServiceException {

        try {
            groupDao.read(title);
        } catch (DaoException e) {
            return;
        }
        log.error("Error - attempt to add already existed group {}", title);
        throw new ServiceException(ERROR_GROUP_ALREADY_EXIST);
    }

    private void checkIsThemeNotExist(String title) throws ServiceException {

        try {
            themeDao.read(title);
        } catch (DaoException e) {
            return;
        }
        log.error("Error - attempt to add already existed theme {}", title);
        throw new ServiceException(ERROR_THEME_ALREADY_EXIST);
    }

    private void checkIsUserNotExist(String login) throws ServiceException {

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
}
