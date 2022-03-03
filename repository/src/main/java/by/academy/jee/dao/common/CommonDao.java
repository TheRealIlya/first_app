package by.academy.jee.dao.common;

public interface CommonDao<T> {

    T create(T t);

    T read(int id);

    T read(String name);

    T update(T newT);

    boolean delete(String name);
}
