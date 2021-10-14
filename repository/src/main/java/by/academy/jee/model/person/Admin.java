package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;

public class Admin extends Person {

    public Admin() {
    }

    public Admin(String login, byte[] pwd, byte[] salt, String name, int age) {
        super(login, pwd, salt, name, age);
        setRole(Role.ADMIN);
    }
}
