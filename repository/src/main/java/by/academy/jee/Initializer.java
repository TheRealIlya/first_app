package by.academy.jee;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.dao.person.PersonDaoForMemoryDatabase;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;

public class Initializer {

    public static PersonDao personDao;

    static {
        personDao = new PersonDaoForMemoryDatabase();

        byte[] salt = {-4, -18, 55, 118, -122, -55, -51, 98};
        byte[] pwd = {48, 89, 82, -76, -32, 66, -128, 119, -101, -86, 11, -43, -122, -113, 96, 57, -2, 3, -57, -24};
        Database.addPerson(new Admin("Admin", pwd, salt, "Ilya", 25, Role.ADMIN)); // Admin, qwe
    }
}
