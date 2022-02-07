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
import org.springframework.stereotype.Component;
import static by.academy.jee.constant.CommonConstant.NAME;
import static by.academy.jee.constant.CommonConstant.ROLE;
import static by.academy.jee.constant.ExceptionConstant.ERROR_NO_STUDENTS_IN_DATABASE;
import static by.academy.jee.constant.JpaQueryConstant.GET_ALL_STUDENTS;
import static by.academy.jee.constant.JpaQueryConstant.LOGIN_FILTER;

@Slf4j
@Component
public class StudentDaoForJpa extends CommonDaoForJpa<Student> implements PersonDao<Student> {

    @Override
    public Student read(String name) {
        EntityManager em = emHelper.get();
        return getStudentByName(name, em);
    }

    @Override
    public List<Student> readAll() {
        EntityManager em = emHelper.get();
        return getAllStudents(em);
    }

    private Student getStudentByName(String name, EntityManager em) {

        String SELECT_ONE_STUDENT = GET_ALL_STUDENTS + LOGIN_FILTER;
        TypedQuery<Student> query = em.createQuery(SELECT_ONE_STUDENT, Student.class);
        query.setParameter(ROLE, Role.STUDENT);
        query.setParameter(NAME, name);
        return query.getSingleResult();
    }

    private List<Student> getAllStudents(EntityManager em) {

        TypedQuery<Student> query = em.createQuery(GET_ALL_STUDENTS, Student.class);
        query.setParameter(ROLE, Role.STUDENT);
        List<Student> students = query.getResultList();
        if (students.size() == 0) {
            log.error(ERROR_NO_STUDENTS_IN_DATABASE);
            throw new DaoException(ERROR_NO_STUDENTS_IN_DATABASE);
        }
        return students;
    }
}
