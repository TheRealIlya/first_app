package by.academy.jee.model.person;

import java.math.BigDecimal;
import java.util.Map;

public class Teacher extends Person {

    private Map<Integer, BigDecimal> salaries;

    public Map<Integer, BigDecimal> getSalaries() {
        return salaries;
    }

    public void setSalaries(Map<Integer, BigDecimal> salaries) {
        this.salaries = salaries;
    }
}
