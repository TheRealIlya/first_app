package by.academy.jee.database;

import by.academy.jee.model.person.Person;

import java.util.HashMap;
import java.util.Map;

public abstract class Database {

    static {

    }

    private static Map<Integer, Person> persons = new HashMap<>();

    private static int count = 1;

    public static void addPerson(Person person) {
        int id = count++;
        person.setId(id);
        persons.put(id, person);
    }

    public static Person getPerson(int id) {
        return persons.get(id);
    }

    public static Person getPerson(String login) {
        for (int key : persons.keySet()) {
            if (login.equals(persons.get(key).getLogin())) {
                return persons.get(key);
            }
        }
        return null;
    }

    public static Map<Integer, Person> getMap() {
        return persons;
    }
}
