package by.academy.jee.database;

import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private Database() {
        //util class
    }

    private static Map<Integer, Admin> admins = new HashMap<>();
    private static Map<Integer, Teacher> teachers = new HashMap<>();
    private static Map<Integer, Student> students = new HashMap<>();

    private static int adminCount = 0;
    private static int teacherCount = 0;
    private static int studentCount = 0;

    public static void addAdmin(Admin admin) {
        int id = adminCount++;
        admin.setId(id);
        admins.put(id, admin);
    }

    public static void addTeacher(Teacher teacher) {
        int id = teacherCount++;
        teacher.setId(id);
        teachers.put(id, teacher);
    }

    public static void addStudent(Student student) {
        int id = studentCount++;
        student.setId(id);
        students.put(id, student);
    }

    public static Teacher getTeacher(int id) {
        return teachers.get(id);
    }

    public static Admin getAdmin(String login) {
        for (int key : admins.keySet()) {
            if (login.equals(admins.get(key).getLogin())) {
                return admins.get(key);
            }
        }
        return null;
    }

    public static Teacher getTeacher(String login) {
        for (int key : teachers.keySet()) {
            if (login.equals(teachers.get(key).getLogin())) {
                return teachers.get(key);
            }
        }
        return null;
    }

    public static Map<Integer, Teacher> getTeacherMap() {
        return teachers;
    }
}
