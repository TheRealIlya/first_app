package by.academy.jee.dao.person.student;

import by.academy.jee.dao.EntityManagerHelper;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.util.DataBaseUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.ERROR_NO_STUDENTS_IN_DATABASE;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_STUDENTS_JPA;

@Slf4j
public class StudentDaoForJpa implements PersonDao<Student> {

    private final EntityManagerHelper helper = EntityManagerHelper.getInstance();
    private final String SELECT_ONE_STUDENT = SELECT_ALL_STUDENTS_JPA + JPA_LOGIN_FILTER;

    private static volatile StudentDaoForJpa instance;

    private StudentDaoForJpa() {
        //singleton
    }

    public static StudentDaoForJpa getInstance() {
        if (instance == null) {
            synchronized ((StudentDaoForJpa.class)) {
                if (instance == null) {
                    instance = new StudentDaoForJpa();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Student student) {
        return save(student);
    }

    @Override
    public Student read(int id) {

        EntityManager em = null;
        Student student = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            student = em.find(Student.class, id);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return student;
    }

    @Override
    public Student read(String name) {

        EntityManager em = null;
        Student student = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            student = getStudentByName(name, em);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return student;
    }

    @Override
    public boolean update(Student newStudent) {
        return save(newStudent);
    }

    @Override
    public boolean delete(String name) {

        EntityManager em = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            Student student = read(name);
            em.remove(student);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return true;
    }

    @Override
    public List<Student> readAll() {

        EntityManager em = null;
        List<Student> students = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            students = getAllStudents(em);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return students;
    }

    private boolean save(Student student) {

        EntityManager em = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            if (student.getId() == null) {
                em.persist(student);
            }
            em.merge(student);
            DataBaseUtil.closeEntityManager(em);
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return true;
    }

    private Student getStudentByName(String name, EntityManager em) {
        TypedQuery<Student> query = em.createQuery(SELECT_ONE_STUDENT, Student.class);
        query.setParameter("role", Role.STUDENT);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    private List<Student> getAllStudents(EntityManager em) {
        TypedQuery<Student> query = em.createQuery(SELECT_ALL_STUDENTS_JPA, Student.class);
        query.setParameter("role", Role.STUDENT);
        List<Student> students = query.getResultList();
        if (students.size() == 0) {
            log.error(ERROR_NO_STUDENTS_IN_DATABASE);
            throw new DaoException(ERROR_NO_STUDENTS_IN_DATABASE);
        }
        return students;
    }
}
