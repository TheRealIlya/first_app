package by.academy.jee.dao.person.teacher;

import by.academy.jee.dao.common.CommonDaoForOrm;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import static by.academy.jee.constant.CommonConstant.NAME;
import static by.academy.jee.constant.CommonConstant.ROLE;
import static by.academy.jee.constant.ExceptionConstant.ERROR_NO_TEACHERS_IN_DATABASE;
import static by.academy.jee.constant.JpaQueryConstant.GET_ALL_TEACHERS;
import static by.academy.jee.constant.JpaQueryConstant.LOGIN_FILTER;

@Repository
@Slf4j
public class TeacherDaoForOrm extends CommonDaoForOrm<Teacher> implements PersonDao<Teacher> {

    @Override
    public Teacher read(String name) {
        return getTeacherByName(name);
    }

    @Override
    public List<Teacher> readAll() {
        return getAllTeachers();
    }

    private Teacher getTeacherByName(String name) {

        String SELECT_ONE_TEACHER = GET_ALL_TEACHERS + LOGIN_FILTER;
        TypedQuery<Teacher> query = em.createQuery(SELECT_ONE_TEACHER, Teacher.class);
        query.setParameter(ROLE, Role.ROLE_TEACHER);
        query.setParameter(NAME, name);
        return query.getSingleResult();
    }

    private List<Teacher> getAllTeachers() {

        TypedQuery<Teacher> query = em.createQuery(GET_ALL_TEACHERS, Teacher.class);
        query.setParameter(ROLE, Role.ROLE_TEACHER);
        List<Teacher> teachers = query.getResultList();
        if (teachers.size() == 0) {
            log.error(ERROR_NO_TEACHERS_IN_DATABASE);
            throw new DaoException(ERROR_NO_TEACHERS_IN_DATABASE);
        }
        return teachers;
    }
}
