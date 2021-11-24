package by.academy.jee.database;

import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;

import java.util.HashMap;
import java.util.Map;
import static by.academy.jee.constant.Constant.ERROR_NO_SUCH_ADMIN;
import static by.academy.jee.constant.Constant.ERROR_NO_SUCH_TEACHER;

public class Database {

    private Map<Integer, Admin> admins = new HashMap<>();
    private Map<Integer, Teacher> teachers = new HashMap<>();
    private Map<Integer, Student> students = new HashMap<>();
    private static int adminCount = 0;
    private static int teacherCount = 0;
    private static int studentCount = 0;

    private static volatile Database instance;

    private Database() {
        //singleton
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public void addAdmin(Admin admin) {
        int id = adminCount++;
        admin.setId(id);
        admins.put(id, admin);
    }

    public void addTeacher(Teacher teacher) {
        int id = teacherCount++;
        teacher.setId(id);
        teachers.put(id, teacher);
    }

    public void addStudent(Student student) {
        int id = studentCount++;
        student.setId(id);
        students.put(id, student);
    }

    public Teacher getTeacher(int id) {
        return teachers.get(id);
    }

    public Admin getAdmin(String login) {
        for (int key : admins.keySet()) {
            if (login.equals(admins.get(key).getLogin())) {
                return admins.get(key);
            }
        }
        throw new DaoException(ERROR_NO_SUCH_ADMIN);
    }

    public Teacher getTeacher(String login) {
        for (int key : teachers.keySet()) {
            if (login.equals(teachers.get(key).getLogin())) {
                return teachers.get(key);
            }
        }
        throw new DaoException(ERROR_NO_SUCH_TEACHER);
    }

    public Map<Integer, Teacher> getTeacherMap() {
        return teachers;
    }
}
