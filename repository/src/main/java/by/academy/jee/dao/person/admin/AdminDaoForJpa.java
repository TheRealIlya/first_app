package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_ADMINS_JPA;

@Component
public class AdminDaoForJpa extends CommonDaoForJpa<Admin> implements PersonDao<Admin> {

    private final String SELECT_ONE_ADMIN = SELECT_ALL_ADMINS_JPA + JPA_LOGIN_FILTER;

    @Override
    public Admin read(String name) {
        EntityManager em = emHelper.get();
        return getAdminByName(name, em);
    }

    @Override
    public List<Admin> readAll() {
        EntityManager em = emHelper.get();
        return getAllAdmins(em);
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
