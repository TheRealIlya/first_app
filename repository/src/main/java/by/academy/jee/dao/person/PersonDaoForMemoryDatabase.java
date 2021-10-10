package by.academy.jee.dao.person;

import by.academy.jee.database.Database;
import by.academy.jee.model.person.Person;

import java.util.List;

public class PersonDaoForMemoryDatabase implements PersonDao{

    @Override
    public boolean create(Person person) {
        return false; //TODO
    }

    @Override
    public Person read(int id) {
        return null; //TODO
    }

    @Override
    public Person read(String name) {
        return Database.getPerson(name);
    }

    @Override
    public boolean update(int id, Person newPerson) {
        return false; //TODO
    }

    @Override
    public boolean delete(int id) {
        return false; //TODO
    }

    @Override
    public List<Person> readAll() {
        return null; //TODO
    }
}
