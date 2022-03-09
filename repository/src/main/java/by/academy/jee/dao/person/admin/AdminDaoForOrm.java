package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.common.CommonDaoForOrm;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import static by.academy.jee.constant.CommonConstant.NAME;
import static by.academy.jee.constant.CommonConstant.ROLE;
import static by.academy.jee.constant.JpaQueryConstant.GET_ALL_ADMINS;
import static by.academy.jee.constant.JpaQueryConstant.LOGIN_FILTER;

@Repository
public class AdminDaoForOrm extends CommonDaoForOrm<Admin> implements PersonDao<Admin> {

    @Override
    public Admin read(String name) {
        return getAdminByName(name);
    }

    @Override
    public List<Admin> readAll() {
        return getAllAdmins();
    }

    private Admin getAdminByName(String name) {

        String SELECT_ONE_ADMIN = GET_ALL_ADMINS + LOGIN_FILTER;
        TypedQuery<Admin> query = em.createQuery(SELECT_ONE_ADMIN, Admin.class);
        query.setParameter(ROLE, Role.ROLE_ADMIN);
        query.setParameter(NAME, name);
        return query.getSingleResult();
    }

    private List<Admin> getAllAdmins() {

        TypedQuery<Admin> query = em.createQuery(GET_ALL_ADMINS, Admin.class);
        query.setParameter(ROLE, Role.ROLE_ADMIN);
        return query.getResultList();
    }
}
