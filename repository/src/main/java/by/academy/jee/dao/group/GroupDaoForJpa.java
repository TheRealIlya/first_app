package by.academy.jee.dao.group;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.MyNoResultException;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Teacher;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class GroupDaoForJpa extends CommonDaoForJpa<Group> implements GroupDao {

    private static volatile GroupDaoForJpa instance;

    private GroupDaoForJpa() {
        //singleton
    }

    public static GroupDaoForJpa getInstance() {

        if (instance == null) {
            synchronized (GroupDaoForJpa.class) {
                if (instance == null) {
                    instance = new GroupDaoForJpa();
                }
            }
        }
        return instance;
    }

    @Override
    public Group read(String title) {
        EntityManager em = emHelper.get();
        try {
            return getGroupByTitle(title, em);
        } catch (NoResultException e) {
            throw new MyNoResultException(e.getMessage());
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Group read(Teacher teacher) {
        EntityManager em = emHelper.get();
        try {
            return getGroupByTeacher(teacher, em);
        } catch (NoResultException e) {
            throw new MyNoResultException(e.getMessage());
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    private Group getGroupByTeacher(Teacher teacher, EntityManager em) {

        TypedQuery<Group> query = em.createQuery("from Group g where g.teacher = ?1", Group.class);
        query.setParameter(1, teacher);
        return query.getSingleResult();
    }

    private Group getGroupByTitle(String title, EntityManager em) {

        TypedQuery<Group> query = em.createQuery("from Group g where g.title = ?1", Group.class);
        query.setParameter(1, title);
        return query.getSingleResult();
    }
}
