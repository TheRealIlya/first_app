package by.academy.jee.web.constant;

public class Constant {

    private Constant() {
        //util class
    }

    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String APPROVE_MESSAGE = "approveMessage";
    public static final String ADD_TEACHER_JSP_URL = "/jsp/admin/addTeacher.jsp";
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
}
