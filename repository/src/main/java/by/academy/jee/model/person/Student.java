package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;
import java.util.Map;

public class Student extends Person {

    private Map<Integer, Integer> marks;

    public Student() {
        setRole(Role.STUDENT);
    }

    public Map<Integer, Integer> getMarks() {
        return marks;
    }

    public void setMarks(Map<Integer, Integer> marks) {
        this.marks = marks;
    }
}
