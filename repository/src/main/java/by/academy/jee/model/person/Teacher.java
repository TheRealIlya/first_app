package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;

import java.util.HashMap;
import java.util.Map;

public class Teacher extends Person {

    public Teacher() {
    }

    public Teacher(String login, byte[] pwd, byte[] salt, String name, int age, Map<Integer, Double> salaries) {
        super(login, pwd, salt, name, age);
        setRole(Role.TEACHER);
        this.salaries = salaries;
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
