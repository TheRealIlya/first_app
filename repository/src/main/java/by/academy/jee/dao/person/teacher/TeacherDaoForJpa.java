package by.academy.jee.dao.person.teacher;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import static by.academy.jee.constant.CommonConstant.NAME;
import static by.academy.jee.constant.CommonConstant.ROLE;
import static by.academy.jee.constant.ExceptionConstant.ERROR_NO_TEACHERS_IN_DATABASE;
import static by.academy.jee.constant.JpaQueryConstant.GET_ALL_TEACHERS;
import static by.academy.jee.constant.JpaQueryConstant.LOGIN_FILTER;

@Slf4j
@Component
public class TeacherDaoForJpa extends CommonDaoForJpa<Teacher> implements PersonDao<Teacher> {

    @Override
    public Teacher read(String name) {
        EntityManager em = emHelper.get();
        return getTeacherByName(name, em);
    }

    @Override
    public List<Teacher> readAll() {
        EntityManager em = emHelper.get();
        return getAllTeachers(em);
    }

    private Teacher getTeacherByName(String name, EntityManager em) {

        String SELECT_ONE_TEACHER = GET_ALL_TEACHERS + LOGIN_FILTER;
        TypedQuery<Teacher> query = em.createQuery(SELECT_ONE_TEACHER, Teacher.class);
        query.setParameter(ROLE, Role.TEACHER);
        query.setParameter(NAME, name);
        return query.getSingleResult();
    }

    private List<Teacher> getAllTeachers(EntityManager em) {

        TypedQuery<Teacher> query = em.createQuery(GET_ALL_TEACHERS, Teacher.class);
        query.setParameter(ROLE, Role.TEACHER);
        List<Teacher> teachers = query.getResultList();
        if (teachers.size() == 0) {
            log.error(ERROR_NO_TEACHERS_IN_DATABASE);
            throw new DaoException(ERROR_NO_TEACHERS_IN_DATABASE);
        }
        return teachers;
    }
}
