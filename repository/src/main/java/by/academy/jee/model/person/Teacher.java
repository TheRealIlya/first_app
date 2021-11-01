package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;

import java.util.HashMap;
import java.util.Map;

public class Teacher extends Person {

    public Teacher() {
        setRole(Role.TEACHER);
    }

    public Teacher withId(int id) {
        setId(id);
        return this;
    }

    public Teacher withLogin(String login) {
        setLogin(login);
        return this;
    }

    public Teacher withPwd(byte[] pwd) {
        setPwd(pwd);
        return this;
    }

    public Teacher withSalt(byte[] salt) {
        setSalt(salt);
        return this;
    }

    public Teacher withName(String name) {
        setName(name);
        return this;
    }

    public Teacher withAge(int age) {
        setAge(age);
        return this;
    }

    public Teacher withSalaries(Map<Integer, Double> salaries) {
        setSalaries(salaries);
        return this;
    }

    private Map<Integer, Double> salaries = new HashMap();

    public Map<Integer, Double> getSalaries() {
        return salaries;
    }

    public void setSalaries(Map<Integer, Double> salaries) {
        this.salaries = salaries;
    }

    @Override
    public String toString() {
        String mapString = "";
        for (int i = 1; i < 13; i++) {
            mapString += "<br>" + i + " - " + String.format("%.2f", salaries.get(i)).replace(',', '.');
        }
        return super.toString() + "<br><br> Salaries:" + mapString;
    }
}
