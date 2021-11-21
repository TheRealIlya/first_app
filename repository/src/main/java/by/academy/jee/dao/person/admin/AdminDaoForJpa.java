package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.EntityManagerHelper;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import by.academy.jee.util.DataBaseUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.JPA_LOGIN_FILTER;
import static by.academy.jee.constant.Constant.SELECT_ALL_ADMINS_JPA;

@Slf4j
public class AdminDaoForJpa implements PersonDao<Admin> {

    private static final String SELECT_ONE_ADMIN = SELECT_ALL_ADMINS_JPA + JPA_LOGIN_FILTER;
    private final EntityManagerHelper helper = EntityManagerHelper.getInstance();

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
        return false;
    }

    @Override
    public Admin read(int id) {

        EntityManager em = null;
        Admin admin = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            admin = em.find(Admin.class, id);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            closeEntityManager(em);
        }
        return admin;
    }

    @Override
    public Admin read(String name) {

        EntityManager em = null;
        Admin admin = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            TypedQuery<Admin> query = em.createQuery(SELECT_ONE_ADMIN, Admin.class);
            query.setParameter("role", Role.ADMIN);
            query.setParameter("name", name);
            admin = query.getSingleResult();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            closeEntityManager(em);
        }
        return admin;
    }

    @Override
    public boolean update(Admin newT) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Admin> readAll() {

        EntityManager em = null;
        List<Admin> admins = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            admins = getAllAdmins(em);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            closeEntityManager(em);
        }
        return admins;
    }

    private List<Admin> getAllAdmins(EntityManager em) {
        TypedQuery<Admin> query = em.createQuery("from Admin a where a.role = :role", Admin.class);
        query.setParameter("role", Role.ADMIN);
        return query.getResultList();
    }

    private void closeEntityManager(EntityManager em) {
        if (em != null) {
            try {
                em.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
