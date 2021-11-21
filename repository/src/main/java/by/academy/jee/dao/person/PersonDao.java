package by.academy.jee.dao.person;

import by.academy.jee.model.person.Person;

import java.util.List;

public interface PersonDao<T extends Person> {

    boolean create(T t);

    T read(int id);

    T read(String name);

    boolean update(T newT);

    boolean delete (String name);

    List<T> readAll();
}
