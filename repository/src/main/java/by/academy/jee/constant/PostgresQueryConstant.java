package by.academy.jee.constant;

public class PostgresQueryConstant {

    private PostgresQueryConstant() {
        //util class
    }

    public static final String GET_ALL_ADMINS = "select u.id u_id, u.login u_login, u.password u_password," +
            "u.salt u_salt, u.name u_name, u.age u_age, r.title r_title " +
            "from users u " +
            "join roles r " +
            "on u.role_id = r.id ";
    public static final String LOGIN_FILTER = "where u.login = ?";
    public static final String INSERT_ADMIN = "insert into users (login, password, salt, name, age, role_id) " +
            "values (?, ?, ?, ?, ?, (select id from roles where title = 'ADMIN'))";
    public static final String INSERT_TEACHER = "insert into users (login, password, salt, name, age, role_id) " +
            "values (?, ?, ?, ?, ?, (select id from roles where title = 'TEACHER'))";
    public static final String INSERT_SALARIES = "insert into salary (salaries_key, value, teacher_id) values (?, ?, " +
            "(select id from users where login = ?))";
    public static final String GET_ALL_TEACHERS = "select u.id u_id, u.login u_login, u.password u_password," +
            "u.salt u_salt, u.name u_name, u.age u_age, r.title r_title, s.salaries_key s_salaries_key, s.value s_value " +
            "from users u " +
            "join roles r " +
            "on u.role_id = r.id " +
            "join salary s " +
            "on u.id = s.teacher_id ";
    public static final String U_ID = "u_id";
    public static final String U_LOGIN = "u_login";
    public static final String U_PASSWORD = "u_password";
    public static final String U_SALT = "u_salt";
    public static final String U_NAME = "u_name";
    public static final String S_SALARIES_KEY = "s_salaries_key";
    public static final String S_VALUE = "s_value";
    public static final String U_AGE = "u_age";
}
