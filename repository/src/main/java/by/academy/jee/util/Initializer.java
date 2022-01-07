package by.academy.jee.util;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.PersonDaoFactory;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Initializer {

    private static final Logger log = LoggerFactory.getLogger(Initializer.class);

    private Initializer() {
        //util class
    }

//    private static PersonDao<Admin> adminDao = PersonDaoFactory.getPersonDao(Role.ADMIN);
//    private static PersonDao<Teacher> teacherDao = PersonDaoFactory.getPersonDao(Role.TEACHER);
//    private static PersonDao<Student> studentDao = PersonDaoFactory.getPersonDao(Role.STUDENT);

    public static void initDatabase() {

//        try {
//            adminDao.read("Admin");
//        } catch (DaoException e) {
//            byte[] salt = PasswordHasher.generateSalt();
//            byte[] pwd = PasswordHasher.getEncryptedPassword("qwe", salt);
//            adminDao.create(new Admin()
//                    .withLogin("Admin")
//                    .withPwd(pwd)
//                    .withSalt(salt)
//                    .withName("Ilya")
//                    .withAge(25)
//                    .withRole(Role.ADMIN)); // Admin, qwe
//        }
//
//        try {
//            teacherDao.read("Mike_");
//        } catch (DaoException e) {
//            byte[] salt = PasswordHasher.generateSalt();
//            byte[] pwd = PasswordHasher.getEncryptedPassword("1234", salt);
//            Map<Integer, Double> salaries = SalaryGenerator.generate(200, 2000);
//            teacherDao.create(new Teacher()
//                    .withLogin("Mike_")
//                    .withPwd(pwd)
//                    .withSalt(salt)
//                    .withName("Mike")
//                    .withAge(35)
//                    .withRole(Role.TEACHER)
//                    .withSalaries(salaries)); // Mike_, 1234
//        }
//
//        try {
//            studentDao.read("Stud");
//        } catch (DaoException e) {
//            byte[] salt = PasswordHasher.generateSalt();
//            byte[] pwd = PasswordHasher.getEncryptedPassword("qwer", salt);
//            studentDao.create(new Student()
//            .withLogin("Stud")
//            .withPwd(pwd)
//            .withSalt(salt)
//            .withName("Dan")
//            .withAge(20)
//            .withRole(Role.STUDENT)); //Stud, qwer
//        }
    }
}
