package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.common.CommonDaoForOrm;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_ADMINS_JPA;

@Repository
public class AdminDaoForOrm extends CommonDaoForOrm<Admin> implements PersonDao<Admin> {

    private final String SELECT_ONE_ADMIN = SELECT_ALL_ADMINS_JPA + JPA_LOGIN_FILTER;

    @Override
    public Admin read(String name) {
        return getAdminByName(name);
    }

    @Override
    public List<Admin> readAll() {
        return getAllAdmins();
    }

    private Admin getAdminByName(String name) {
        TypedQuery<Admin> query = em.createQuery(SELECT_ONE_ADMIN, Admin.class);
        query.setParameter("role", Role.ADMIN);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    private List<Admin> getAllAdmins() {
        TypedQuery<Admin> query = em.createQuery(SELECT_ALL_ADMINS_JPA, Admin.class);
        query.setParameter("role", Role.ADMIN);
        return query.getResultList();
    }
}
