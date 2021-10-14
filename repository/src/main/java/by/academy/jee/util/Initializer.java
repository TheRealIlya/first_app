package by.academy.jee.util;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.PersonDaoForMemoryDatabase;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Teacher;

public class Initializer {

    public static PersonDao personDao;

    static {
        personDao = new PersonDaoForMemoryDatabase();
        initDatabase();
    }

    private static void initDatabase() {

        byte[] salt = PasswordHasher.generateSalt();
        byte[] pwd = PasswordHasher.getEncryptedPassword("qwe", salt);
        Database.addPerson(new Admin("Admin", pwd, salt, "Ilya", 25)); // Admin, qwe

        salt = PasswordHasher.generateSalt();
        pwd = PasswordHasher.getEncryptedPassword("1234", salt);
        Database.addPerson(new Teacher("Mike_", pwd, salt, "Mike", 35,
                SalaryGenerator.generate(200, 2000))); // Mike_, 1234
    }
}
