package by.academy.jee.constant;

public class ExceptionConstant {

    private ExceptionConstant() {
        //util class
    }

    public static final String GRADE_UPDATE_ERROR = "Error - Grade id must be equal with id in path";
    public static final String GROUP_UPDATE_ERROR = "Error - Group id must be equal with id in path";
    public static final String PERSON_UPDATE_ERROR = "Error - Person's id must be equal with id in path";
    public static final String THEME_UPDATE_ERROR = "Error - Theme id must be equal with id in path";
    public static final String ERROR_WRONG_MONTHS_INPUT = "Error - wrong months input";
    public static final String ERROR_WRONG_MONTHS_FORMAT = "Error - wrong months format - must be numbers";
    public static final String ERROR_WRONG_NUMBERS_FORMAT = "Error - wrong numbers format";
    public static final String ERROR_AGE_MUST_BE_A_NUMBER = "Error - age must be a number!";
    public static final String ERROR_WRONG_GRADE_FORMAT = "Error - wrong grade format - must be a number from 1 to 10";
    public static final String ERROR_WRONG_SALARIES_INPUT = "Error - wrong salaries input";
    public static final String ERROR_WRONG_SALARIES_LOGIC = "Error - salaries can't be < 0 and minimal salary must be " +
            "lower than maximal salary!";
    public static final String ERROR_WRONG_PASSWORD = "Error - wrong password";
    public static final String USER_IS_ALREADY_EXIST = "Error - user with this login is already exist";
    public static final String ERROR_SALARIES_MUST_BE_NUMBERS = "Error - salaries must be numbers!";
    public static final String ERROR_NO_STUDENT_IN_GROUP = "Error - no such student in current group";
    public static final String ERROR_NO_THEME_IN_GROUP = "Error - this group doesn't contain such theme";
    public static final String ERROR_GROUP_ALREADY_HAS_A_TEACHER = "Error - this group already has a teacher";
    public static final String ERROR_GROUP_ALREADY_EXIST = "Error - this group is already exist";
    public static final String ERROR_THEME_ALREADY_EXIST = "Error - this theme is already exist";
    public static final String NO_SUCH_ENTITY_WITH_THIS_ID = "No such entity with this id";
    public static final String ERROR_NO_SUCH_ADMIN = "Error - no such admin in database";
    public static final String ERROR_NO_SUCH_TEACHER = "Error - no such teacher in database";
    public static final String USER_CREATE_TRANSACTION_ERROR = "User creation error - something went wrong with transaction";
    public static final String ERROR_NO_TEACHERS_IN_DATABASE = "Error - no teachers in database";
    public static final String COULDNT_CLOSE_SOME_CLOSEABLE_ELEMENT = "Couldn't close some closeable element";
    public static final String FAILED_TO_ROLLBACK = "Failed to rollback";
    public static final String WRONG_ALGORITHM = "Wrong algorithm";
    public static final String WRONG_ENCRYPTING_ALGORITHMS = "Wrong encrypting algorithms";
    public static final String ERROR_NO_STUDENTS_IN_DATABASE = "Error - no students in database";
}
