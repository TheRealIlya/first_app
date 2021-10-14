package by.academy.jee.util;

import java.util.HashMap;
import java.util.Map;

public class SalaryGenerator {

    public static Map<Integer, Double> generate(double minSalary, double maxSalary) {
        Map<Integer, Double> salaries = new HashMap();
        for (int i = 1; i < 13; i++) {
            double salary = (minSalary + Math.random() * (maxSalary - minSalary));
            salaries.put(i, salary);
        }
        return salaries;
    }
}
