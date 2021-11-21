package by.academy.jee.dao.person.teacher;

import by.academy.jee.dao.EntityManagerHelper;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.PersonDaoException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.ERROR_NO_SUCH_ADMIN;

@Slf4j
public class TeacherDaoForJpa implements PersonDao<Teacher> {

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
        return false;
    }

    @Override
    public Teacher read(int id) {

        EntityManager em = null;
        Teacher teacher = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            teacher = em.find(Teacher.class, id);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            safeRollback(em, e);
        } finally {
            closeEntityManager(em);
        }
        return teacher;
    }

    @Override
    public Teacher read(String name) {

        List<Teacher> teachers = readAll();
        return teachers.stream()
                .filter(teacher -> name.equals(teacher.getLogin()))
                .findAny()
                .orElseThrow(() -> new PersonDaoException(ERROR_NO_SUCH_ADMIN));
    }

    @Override
    public boolean update(Teacher newT) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Teacher> readAll() {

        EntityManager em = null;
        List<Teacher> teachers = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            teachers = getAllTeachers(em);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            safeRollback(em, e);
        } finally {
            closeEntityManager(em);
        }
        return teachers;
    }

    private void safeRollback(EntityManager em, Exception e) {
        if (em != null) {
            em.getTransaction().rollback();
        }
        log.error(e.getMessage(), e);
        throw new PersonDaoException(e.getMessage(), e);
    }

    private void closeEntityManager(EntityManager em) {
        if (em != null) {
            try {
                em.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private List<Teacher> getAllTeachers(EntityManager em) {
        TypedQuery<Teacher> query = em.createQuery("from Teacher a where a.role = :role", Teacher.class);
        query.setParameter("role", Role.TEACHER);
        return query.getResultList();
    }
}
