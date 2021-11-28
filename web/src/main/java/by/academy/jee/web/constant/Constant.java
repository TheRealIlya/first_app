package by.academy.jee.web.constant;

public class Constant {

    private Constant() {
        //util class
    }

    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String APPROVE_MESSAGE = "approveMessage";
    public static final String RESULT = "result";
    public static final String ADD_TEACHER_JSP_URL = "/jsp/admin/addTeacher.jsp";
    public static final String CHANGE_GROUP_JSP_URL = "/jsp/teacher/changeGroup.jsp";
    public static final String CREATE_GRADE_JSP_URL = "/jsp/teacher/createGrade.jsp";
    public static final String ADD_STUDENT_JSP_URL = "/jsp/admin/addStudent.jsp";
    public static final String GROUP_INFO_JSP_URL = "/jsp/teacher/groupInfo.jsp";
    public static final String CHECK_GRADES_JSP_URL = "/jsp/student/checkGrades.jsp";
    public static final String GROUP_INFO_ERROR_PAGE_JSP_URL = "/jsp/teacher/groupInfoErrorPage.jsp";
    public static final String LOGIN_JSP_URL = "/jsp/common/login.jsp";
    public static final String NO_SUCH_USER_IN_DATABASE = "No such user in database";
    public static final String USER_IS_ALREADY_EXIST = "Error - user with this login is already exist";
    public static final String ADMIN = "ADMIN";
    public static final String TEACHER = "TEACHER";
    public static final String STUDENT = "STUDENT";
    public static final String ADMIN_MENU_JSP_URL = "/jsp/admin/adminMenu.jsp";
    public static final String TEACHER_MENU_JSP_URL = "/jsp/teacher/teacherMenu.jsp";
    public static final String STUDENT_MENU_JSP_URL = "/jsp/student/studentMenu.jsp";
    public static final String AVG_SALARY_JSP_URL = "/jsp/admin/avgSalary.jsp";
    public static final String ERROR_WRONG_MONTHS_INPUT = "Error - wrong months input";
    public static final String ERROR_WRONG_MONTHS_FORMAT = "Error - wrong months format - must be numbers";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String USER_NAME = "userName";
    public static final String AGE = "age";
    public static final String MIN_SALARY = "minSalary";
    public static final String MAX_SALARY = "maxSalary";
    public static final String ERROR_WRONG_NUMBERS_FORMAT = "Error - wrong numbers format";
    public static final String SALARIES_MUST_BE_NUMBERS = "Error - salaries must be numbers!";
    public static final String ERROR_AGE_MUST_BE_A_NUMBER = "Error - age must be a number!";
    public static final String ERROR_WRONG_GRADE_FORMAT = "Error - wrong grade format - must be a number from 1 to 10";
    public static final String ERROR_WRONG_SALARIES_INPUT = "Error - wrong salaries input";
    public static final String ERROR_WRONG_SALARIES_LOGIC = "Error - salaries can't be < 0 and minimal salary must be " +
            "lower than maximal salary!";
    public static final String ERROR_INCORRECT_ROLE = "Error - role is filled incorrectly. Please contact admin to fix it";
    public static final String ERROR_WRONG_PASSWORD = "Error - wrong password";
    public static final String TEACHER_IS_SUCCESSFULLY_ADDED = "Teacher is successfully added!";
    public static final String STUDENT_IS_SUCCESSFULLY_ADDED = "Student is successfully added!";
    public static final String FIRST_MONTH = "firstMonth";
    public static final String LAST_MONTH = "lastMonth";
    public static final String AVERAGE_SALARY_CALCULATED = "Average salary is successfully calculated";
    public static final String HOME_PAGE_JSP_URL = "/jsp/common/homePage.jsp";
    public static final String ALREADY_LOGGED_IN_JSP_URL = "/jsp/common/alreadyLoggedIn.jsp";
    public static final String USER_IS_LOGGED_OUT = "User is logged out";
    public static final String SUCCESSFULLY_LOGGED_OUT = "Successfully logged out!";
    public static final String USER_INFO_JSP_URL = "/jsp/common/userInfo.jsp";
    public static final String USER = "user";
}
