package by.academy.jee.dao.person.admin;

import by.academy.jee.dao.EntityManagerHelper;
import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.exception.PersonDaoException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.ERROR_NO_SUCH_ADMIN;

@Slf4j
public class AdminDaoForJpa implements PersonDao<Admin> {

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
            safeRollback(em, e);
        } finally {
            closeEntityManager(em);
        }
        return admin;
    }

    @Override
    public Admin read(String name) {

        List<Admin> admins = readAll();
        return admins.stream()
                .filter(admin -> name.equals(admin.getLogin()))
                .findAny()
                .orElseThrow(() -> new PersonDaoException(ERROR_NO_SUCH_ADMIN));
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
            safeRollback(em, e);
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

    private void safeRollback(EntityManager em, Exception e) {
        if (em != null) {
            em.getTransaction().rollback();
        }
        log.error(e.getMessage(), e);
        throw new PersonDaoException(e.getMessage(), e);
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
