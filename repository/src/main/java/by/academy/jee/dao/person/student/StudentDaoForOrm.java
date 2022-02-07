package by.academy.jee.dao.person.student;

import by.academy.jee.dao.common.CommonDaoForOrm;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import static by.academy.jee.constant.CommonConstant.NAME;
import static by.academy.jee.constant.CommonConstant.ROLE;
import static by.academy.jee.constant.ExceptionConstant.ERROR_NO_STUDENTS_IN_DATABASE;
import static by.academy.jee.constant.JpaQueryConstant.GET_ALL_STUDENTS;
import static by.academy.jee.constant.JpaQueryConstant.LOGIN_FILTER;

@Repository
@Slf4j
public class StudentDaoForOrm extends CommonDaoForOrm<Student> implements PersonDao<Student> {

    @Override
    public Student read(String name) {
        return getStudentByName(name);
    }

    @Override
    public List<Student> readAll() {
        return getAllStudents();
    }

    private Student getStudentByName(String name) {
        String SELECT_ONE_STUDENT = GET_ALL_STUDENTS + LOGIN_FILTER;
        TypedQuery<Student> query = em.createQuery(SELECT_ONE_STUDENT, Student.class);
        query.setParameter(ROLE, Role.STUDENT);
        query.setParameter(NAME, name);
        return query.getSingleResult();
    }

    private List<Student> getAllStudents() {
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
