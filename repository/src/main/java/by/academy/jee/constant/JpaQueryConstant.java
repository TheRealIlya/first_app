package by.academy.jee.constant;

public class JpaQueryConstant {

    private JpaQueryConstant() {
        //util class
    }

    public static final String UPDATE_GRADE_VALUE = "update Grade set value = ?1 where id = ?2";
    public static final String GET_ALL_GRADES = "from Grade";
    public static final String GET_ALL_GROUPS = "from Group";
    public static final String GET_GROUP_BY_TEACHER = "from Group g where g.teacher = ?1";
    public static final String GET_GROUP_BY_TITLE = "from Group g where g.title = ?1";
    public static final String GET_ALL_TEACHERS = "from Teacher u where u.role = :role";
    public static final String GET_ALL_ADMINS = "from Admin u where u.role = :role";
    public static final String GET_ALL_STUDENTS = "from Student u where u.role = :role";
    public static final String LOGIN_FILTER = " and u.login = :name";
    public static final String GET_ALL_THEMES = "from Theme";
    public static final String GET_THEME_BY_TITLE = "from Theme t where t.title = ?1";
}
