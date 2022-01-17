package by.academy.jee.dao.common;

import by.academy.jee.exception.DaoException;
import by.academy.jee.model.AbstractEntity;
import by.academy.jee.util.ThreadLocalForEntityManager;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;

public abstract class CommonDaoForJpa<T extends AbstractEntity> implements CommonDao<T> {

    protected final ThreadLocalForEntityManager emHelper = ThreadLocalForEntityManager.getInstance();

    @Override
    public T create(T t) {
        return save(t);
    }

    @Override
    public T read(int id) {

        EntityManager em = emHelper.get();
        T t = em.find(getType(), id);
        if (t == null) {
            throw new DaoException("No such entity with this id");
        }
        return t;
    }

    @Override
    public abstract T read(String name);

    @Override
    public T update(T newT) {
        return save(newT);
    }

    @Override
    public boolean delete(String name) {

        EntityManager em = emHelper.get();
        T t = read(name);
        em.remove(t);
        return true;
    }

    protected T save(T t) {

        EntityManager em = emHelper.get();
        if (t.getId() == null) {
            em.persist(t);
        } else {
            em.merge(t);
        }
        return t;
    }

    private Class<T> getType() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }
}
