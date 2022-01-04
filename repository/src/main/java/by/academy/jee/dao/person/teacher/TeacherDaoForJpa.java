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
import static by.academy.jee.constant.Constant.ERROR_NO_TEACHERS_IN_DATABASE;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_TEACHERS_JPA;

@Slf4j
public class TeacherDaoForJpa extends CommonDaoForJpa<Teacher> implements PersonDao<Teacher> {

    private final String SELECT_ONE_TEACHER = SELECT_ALL_TEACHERS_JPA + JPA_LOGIN_FILTER;

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
    public Teacher read(String name) {

        EntityManager em = emHelper.get();
        try {
            return getTeacherByName(name, em);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Teacher> readAll() {

        EntityManager em = emHelper.get();
        try {
            return getAllTeachers(em);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
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
