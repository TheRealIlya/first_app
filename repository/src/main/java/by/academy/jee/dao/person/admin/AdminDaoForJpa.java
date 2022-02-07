package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import static by.academy.jee.constant.CommonConstant.NAME;
import static by.academy.jee.constant.CommonConstant.ROLE;
import static by.academy.jee.constant.JpaQueryConstant.GET_ALL_ADMINS;
import static by.academy.jee.constant.JpaQueryConstant.LOGIN_FILTER;

@Component
public class AdminDaoForJpa extends CommonDaoForJpa<Admin> implements PersonDao<Admin> {

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

        String SELECT_ONE_ADMIN = GET_ALL_ADMINS + LOGIN_FILTER;
        TypedQuery<Admin> query = em.createQuery(SELECT_ONE_ADMIN, Admin.class);
        query.setParameter(ROLE, Role.ADMIN);
        query.setParameter(NAME, name);
        return query.getSingleResult();
    }

    private List<Admin> getAllAdmins(EntityManager em) {

        TypedQuery<Admin> query = em.createQuery(GET_ALL_ADMINS, Admin.class);
        query.setParameter(ROLE, Role.ADMIN);
        return query.getResultList();
    }
}
