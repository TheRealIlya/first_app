package by.academy.jee.util;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.admin.AdminDaoForMemoryDatabase;
import by.academy.jee.dao.person.teacher.TeacherDaoForMemoryDatabase;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Teacher;

public abstract class Initializer {

    public static PersonDao<Admin> adminDao;
    public static PersonDao<Teacher> teacherDao;

    static {
        adminDao = new AdminDaoForMemoryDatabase();
        teacherDao = new TeacherDaoForMemoryDatabase();
        initDatabase();
    }

    private static void initDatabase() {

        byte[] salt = PasswordHasher.generateSalt();
        byte[] pwd = PasswordHasher.getEncryptedPassword("qwe", salt);
        Database.addAdmin(new Admin("Admin", pwd, salt, "Ilya", 25)); // Admin, qwe

        salt = PasswordHasher.generateSalt();
        pwd = PasswordHasher.getEncryptedPassword("1234", salt);
        Database.addTeacher(new Teacher("Mike_", pwd, salt, "Mike", 35,
                SalaryGenerator.generate(200, 2000))); // Mike_, 1234
    }
}
