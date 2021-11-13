package by.academy.jee.model.person;

import java.util.Map;

public class Student extends Person {

    private Map<Integer, Integer> marks;

    public Map<Integer, Integer> getMarks() {
        return marks;
    }

    public void setMarks(Map<Integer, Integer> marks) {
        this.marks = marks;
    }
}
