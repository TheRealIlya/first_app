package by.academy.jee.dao.group;

import by.academy.jee.dao.EntityManagerHelper;
import by.academy.jee.exception.MyNoResultException;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.util.DataBaseUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class GroupDaoForJpa implements GroupDao {

    private final EntityManagerHelper helper = EntityManagerHelper.getInstance();

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
    public Group create(Group group) {
        save(group);
        return group;
    }

    @Override
    public Group read(int id) {

        EntityManager em = null;
        Group group = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            group = em.find(Group.class, id);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return group;
    }

    @Override
    public Group read(Teacher teacher) {
        EntityManager em = null;
        Group group = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            group = getGroupByTeacher(teacher, em);
            DataBaseUtil.closeEntityManager(em);
        } catch (NoResultException e) {
            DataBaseUtil.closeEntityManager(em);
            throw new MyNoResultException(e.getMessage());
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return group;
    }

    @Override
    public boolean update(Group newGroup) {
        return save(newGroup);
    }

    private boolean save(Group group) {

        EntityManager em = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            if (group.getId() == null) {
                em.persist(group);
            }
            em.merge(group);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return true;
    }

    private Group getGroupByTeacher(Teacher teacher, EntityManager em) {

        TypedQuery<Group> query = em.createQuery("from Group g where g.teacher = ?1", Group.class);
        query.setParameter(1, teacher);
        return query.getSingleResult();
    }
}
