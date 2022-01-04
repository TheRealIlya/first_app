package by.academy.jee.dao.person.student;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.ERROR_NO_STUDENTS_IN_DATABASE;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_STUDENTS_JPA;

@Slf4j
public class StudentDaoForJpa extends CommonDaoForJpa<Student> implements PersonDao<Student> {

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
    public Student read(String name) {

        EntityManager em = emHelper.get();
        try {
            return getStudentByName(name, em);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Student> readAll() {

        EntityManager em = emHelper.get();
        try {
            return getAllStudents(em);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
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
