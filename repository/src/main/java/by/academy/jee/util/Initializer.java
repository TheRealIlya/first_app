package by.academy.jee.util;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.admin.AdminDaoForMemoryDatabase;
import by.academy.jee.dao.person.teacher.TeacherDaoForMemoryDatabase;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Initializer {

    private Initializer() {
        //util class
    }

    private static final Logger log = LoggerFactory.getLogger(Initializer.class);

    private static PersonDao<Admin> adminDao;

    private static PersonDao<Teacher> teacherDao;

    static {
        setAdminDao(new AdminDaoForMemoryDatabase());
        setTeacherDao(new TeacherDaoForMemoryDatabase());
        log.info("Static DAO initialization completed");
        initDatabase();
    }

    public static PersonDao<Admin> getAdminDao() {
        return adminDao;
    }

    public static void setAdminDao(PersonDao<Admin> adminDao) {
        Initializer.adminDao = adminDao;
    }

    public static PersonDao<Teacher> getTeacherDao() {
        return teacherDao;
    }

    public static void setTeacherDao(PersonDao<Teacher> teacherDao) {
        Initializer.teacherDao = teacherDao;
    }

    private static void initDatabase() {

        byte[] salt = PasswordHasher.generateSalt();
        byte[] pwd = PasswordHasher.getEncryptedPassword("qwe", salt);
        Database.addAdmin(new Admin()
                .withLogin("Admin")
                .withPwd(pwd)
                .withSalt(salt)
                .withName("Ilya")
                .withAge(25)
                .withRole(Role.ADMIN)); // Admin, qwe

        salt = PasswordHasher.generateSalt();
        pwd = PasswordHasher.getEncryptedPassword("1234", salt);
        Map<Integer, Double> salaries = SalaryGenerator.generate(200, 2000);
        Database.addTeacher(new Teacher()
                .withLogin("Mike_")
                .withPwd(pwd)
                .withSalt(salt)
                .withName("Mike")
                .withAge(35)
                .withSalaries(salaries)); // Mike_, 1234
        log.info("In-memory database initialized");
    }
}
