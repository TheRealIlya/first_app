package by.academy.jee.database;

import by.academy.jee.model.person.Person;

import java.util.HashMap;
import java.util.Map;

public class Persons {

    private Map<Integer, Person> persons = new HashMap<>();

    private static int count = 1;

    public void addPerson(Person person) {
        int id = count++;
        person.setId(id);
        persons.put(id, person);
    }

    public Person getPerson(int id) {
        return persons.get(id);
    }
}
