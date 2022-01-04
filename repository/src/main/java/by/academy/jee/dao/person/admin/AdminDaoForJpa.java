package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_ADMINS_JPA;

@Slf4j
public class AdminDaoForJpa extends CommonDaoForJpa<Admin> implements PersonDao<Admin> {

    private final String SELECT_ONE_ADMIN = SELECT_ALL_ADMINS_JPA + JPA_LOGIN_FILTER;

    private static volatile AdminDaoForJpa instance;

    private AdminDaoForJpa() {
        //singleton
    }

    public static AdminDaoForJpa getInstance() {

        if (instance == null) {
            synchronized (AdminDaoForJpa.class) {
                if (instance == null) {
                    instance = new AdminDaoForJpa();
                }
            }
        }
        return instance;
    }

    @Override
    public Admin read(String name) {

        EntityManager em = emHelper.get();
        try {
            return getAdminByName(name, em);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Admin> readAll() {

        EntityManager em = emHelper.get();
        try {
            return getAllAdmins(em);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    private Admin getAdminByName(String name, EntityManager em) {
        TypedQuery<Admin> query = em.createQuery(SELECT_ONE_ADMIN, Admin.class);
        query.setParameter("role", Role.ADMIN);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    private List<Admin> getAllAdmins(EntityManager em) {
        TypedQuery<Admin> query = em.createQuery(SELECT_ALL_ADMINS_JPA, Admin.class);
        query.setParameter("role", Role.ADMIN);
        return query.getResultList();
    }
}
