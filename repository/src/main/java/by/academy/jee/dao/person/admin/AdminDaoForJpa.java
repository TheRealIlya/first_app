package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.DaoException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.util.ThreadLocalForEntityManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_ADMINS_JPA;

@Slf4j
public class AdminDaoForJpa implements PersonDao<Admin> {

    private final String SELECT_ONE_ADMIN = SELECT_ALL_ADMINS_JPA + JPA_LOGIN_FILTER;
    private final ThreadLocalForEntityManager emHelper = ThreadLocalForEntityManager.getInstance();

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
    public boolean create(Admin admin) {
        return save(admin);
    }

    @Override
    public Admin read(int id) {

        EntityManager em = emHelper.get();
        try {
            return em.find(Admin.class, id);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
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
    public boolean update(Admin newAdmin) {
        return save(newAdmin);
    }

    @Override
    public boolean delete(String name) {

        EntityManager em = emHelper.get();
        try {
            Admin admin = read(name);
            em.remove(admin);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return true;
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

    private boolean save(Admin admin) {

        EntityManager em = emHelper.get();
        try {
            if (admin.getId() == null) {
                em.persist(admin);
            }
            em.merge(admin);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
        return true;
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
