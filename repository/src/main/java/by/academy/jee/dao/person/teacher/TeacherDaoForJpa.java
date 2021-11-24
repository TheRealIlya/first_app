package by.academy.jee.dao.person.teacher;

import by.academy.jee.dao.EntityManagerHelper;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.util.DataBaseUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.ERROR_NO_TEACHERS_IN_DATABASE;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_TEACHERS_JPA;

@Slf4j
public class TeacherDaoForJpa implements PersonDao<Teacher> {

    private final String SELECT_ONE_TEACHER = SELECT_ALL_TEACHERS_JPA + JPA_LOGIN_FILTER;
    private final EntityManagerHelper helper = EntityManagerHelper.getInstance();

    private static volatile TeacherDaoForJpa instance;

    private TeacherDaoForJpa() {
        //singleton
    }

    public static TeacherDaoForJpa getInstance() {

        if (instance == null) {
            synchronized (TeacherDaoForJpa.class) {
                if (instance == null) {
                    instance = new TeacherDaoForJpa();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Teacher teacher) {
        return save(teacher);
    }

    @Override
    public Teacher read(int id) {

        EntityManager em = null;
        Teacher teacher = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            teacher = em.find(Teacher.class, id);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return teacher;
    }

    @Override
    public Teacher read(String name) {

        EntityManager em = null;
        Teacher teacher = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            teacher = getTeacherByName(name, em);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return teacher;
    }

    @Override
    public boolean update(Teacher newTeacher) {
        return save(newTeacher);
    }

    @Override
    public boolean delete(String name) {

        EntityManager em = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            Teacher teacher = read(name);
            em.remove(teacher);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return true;
    }

    @Override
    public List<Teacher> readAll() {

        EntityManager em = null;
        List<Teacher> teachers = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            teachers = getAllTeachers(em);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return teachers;
    }

    private boolean save(Teacher teacher) {

        EntityManager em = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            if (teacher.getId() == null) {
                em.persist(teacher);
            }
            em.merge(teacher);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return true;
    }

    private Teacher getTeacherByName(String name, EntityManager em) {
        TypedQuery<Teacher> query = em.createQuery(SELECT_ONE_TEACHER, Teacher.class);
        query.setParameter("role", Role.TEACHER);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    private List<Teacher> getAllTeachers(EntityManager em) {
        TypedQuery<Teacher> query = em.createQuery(SELECT_ALL_TEACHERS_JPA, Teacher.class);
        query.setParameter("role", Role.TEACHER);
        List<Teacher> teachers = query.getResultList();
        if (teachers.size() == 0) {
            log.error(ERROR_NO_TEACHERS_IN_DATABASE);
            throw new DaoException(ERROR_NO_TEACHERS_IN_DATABASE);
        }
        return teachers;
    }
}
