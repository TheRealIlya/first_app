package by.academy.jee.constant;

public class Constant {

    private Constant() {
        //util class
    }

    public static final String SELECT_ALL_ADMINS_POSTGRES = "select u.id u_id, u.login u_login, u.password u_password," +
            "u.salt u_salt, u.name u_name, u.age u_age, r.title r_title " +
            "from \"user\" u " +
            "join \"role\" r " +
            "on u.role_id = r.id ";
    public static final String LOGIN_FILTER_POSTGRES = "where u.login = ?";
    public static final String INSERT_ADMIN_POSTGRES = "insert into \"user\" (login, password, salt, name, age, role_id) " +
            "values (?, ?, ?, ?, ?, (select id from role where title = 'ADMIN'))";
    public static final String INSERT_TEACHER_POSTGRES = "insert into \"user\" (login, password, salt, name, age, role_id) " +
            "values (?, ?, ?, ?, ?, (select id from role where title = 'TEACHER'))";
    public static final String U_ID = "u_id";
    public static final String U_LOGIN = "u_login";
    public static final String U_PASSWORD = "u_password";
    public static final String U_SALT = "u_salt";
    public static final String U_NAME = "u_name";
    public static final String S_MONTH = "s_month";
    public static final String S_VALUE = "s_value";
    public static final String U_AGE = "u_age";
    public static final String R_TITLE = "r_title";
    public static final String ADMIN = "ADMIN";
    public static final String TEACHER = "TEACHER";
    public static final String STUDENT = "STUDENT";
    public static final String REPOSITORY_PROPERTIES = "repository.properties";
    public static final String REPOSITORY_TYPE = "repository.type";
    public static final String POSTGRES_URL = "postgres.url";
    public static final String POSTGRES_USER = "postgres.user";
    public static final String POSTGRES_PASSWORD = "postgres.password";
    public static final String POSTGRES_DRIVER = "postgres.driver";
    public static final String SECURE_RANDOM_TYPE = "SHA1PRNG";
    public static final String WRONG_ALGORITHM = "Wrong algorithm";
    public static final String HASHING_ALGORITHM = "PBKDF2WithHmacSHA1";
    public static final String WRONG_ENCRYPTING_ALGORITHMS = "Wrong encrypting algorithms";
    public static final String SELECT_ALL_TEACHERS_POSTGRES = "select u.id u_id, u.login u_login, u.password u_password," +
            "u.salt u_salt, u.name u_name, u.age u_age, r.title r_title, s.month s_month, s.value s_value " +
            "from \"user\" u " +
            "join \"role\" r " +
            "on u.role_id = r.id " +
            "join salary s " +
            "on u.id = s.teacher_id ";
    public static final String INSERT_SALARIES_POSTGRES = "insert into salary (month, value, teacher_id) values (?, ?, " +
            "(select id from \"user\" where login = ?))";
    public static final String USER_CREATE_TRANSACTION_ERROR = "User creation error - something went wrong with transaction";
    public static final String ERROR_NO_TEACHERS_IN_DATABASE = "Error - no teachers in database";
    public static final String ERROR_NO_SUCH_TEACHER = "Error - no such teacher in database";
    public static final String ERROR_NO_SUCH_ADMIN = "Error - no such admin in database";
    public static final String COULDNT_CLOSE_SOME_CLOSEABLE_ELEMENT = "Couldn't close some closeable element";
    public static final String FAILED_TO_ROLLBACK = "Failed to rollback";
}