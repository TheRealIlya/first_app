package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;

import java.util.Objects;

public abstract class Person {

    private int id;
    private String login;
    private byte[] pwd;
    private byte[] salt;
    private String name;
    private int age;
    private Role role;

    public Person() {
    }

    public Person(String login, byte[] pwd, byte[] salt, String name, int age, Role role) {
        this.login = login;
        this.pwd = pwd;
        this.salt = salt;
        this.name = name;
        this.age = age;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getPwd() {
        return pwd;
    }

    public void setPwd(byte[] pwd) {
        this.pwd = pwd;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
