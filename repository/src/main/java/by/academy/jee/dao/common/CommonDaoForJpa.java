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
        try {
            return em.find(getType(), id);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
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
        try {
            T t = read(name);
            em.remove(t);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return true;
    }

    protected T save(T t) {

        EntityManager em = emHelper.get();
        try {
            if (t.getId() == null) {
                em.persist(t);
            } else {
                em.merge(t);
            }
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return t;
    }

    private Class<T> getType() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }
}
