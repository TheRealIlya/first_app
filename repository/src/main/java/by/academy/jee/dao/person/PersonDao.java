package by.academy.jee.dao.person;

import by.academy.jee.model.person.Person;

import java.util.List;

public interface PersonDao {

    boolean create(Person person);

    Person read(int id);

    Person read(String name);

    boolean update(int id, Person newPerson);

    boolean delete (int id);

    List<Person> readAll();
}
