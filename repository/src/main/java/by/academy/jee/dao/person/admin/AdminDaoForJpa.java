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

    private final String SELECT_ONE_ADMIN = SELECT_ALL_ADMINS_JPA + JPA_LOGIN_FILTER;
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
        return save(admin);
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
            DataBaseUtil.closeEntityManager(em);
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
            admin = getAdminByName(name, em);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.closeEntityManager(em);
        }
        return admin;
    }

    @Override
    public boolean update(Admin newAdmin) {
        return save(newAdmin);
    }

    @Override
    public boolean delete(String name) {

        EntityManager em = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            Admin admin = read(name);
            em.remove(admin);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.closeEntityManager(em);
        }
        return true;
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
            DataBaseUtil.closeEntityManager(em);
        }
        return admins;
    }

    private boolean save(Admin admin) {

        EntityManager em = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            if (admin.getId() == null) {
                em.persist(admin);
            }
            em.merge(admin);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e);
        } finally {
            DataBaseUtil.closeEntityManager(em);
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
