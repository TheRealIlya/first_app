package by.academy.jee.dao.theme;

import by.academy.jee.dao.EntityManagerHelper;
import by.academy.jee.exception.MyNoResultException;
import by.academy.jee.model.theme.Theme;
import by.academy.jee.util.DataBaseUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ThemeDaoForJpa implements ThemeDao {

    private final EntityManagerHelper helper = EntityManagerHelper.getInstance();

    private static volatile ThemeDaoForJpa instance;

    private ThemeDaoForJpa() {
        //singleton
    }

    public static ThemeDaoForJpa getInstance() {

        if (instance == null) {
            synchronized (ThemeDaoForJpa.class) {
                if (instance == null) {
                    instance = new ThemeDaoForJpa();
                }
            }
        }
        return instance;
    }

    @Override
    public Theme read(String title) {

        EntityManager em = null;
        Theme theme = null;
        try {
            em = helper.getEntityManager();
            em.getTransaction().begin();
            theme = getThemeByTitle(title, em);
            DataBaseUtil.closeEntityManager(em);
        } catch (NoResultException e) {
            DataBaseUtil.closeEntityManager(em);
            throw new MyNoResultException(e.getMessage());
        } catch (Exception e) {
            DataBaseUtil.rollBack(em, e, "No theme with such title");
        } finally {
            DataBaseUtil.finallyCloseEntityManager(em);
        }
        return theme;
    }

    private Theme getThemeByTitle(String title, EntityManager em) {

        TypedQuery<Theme> query = em.createQuery("from Theme t where t.title = ?1", Theme.class);
        query.setParameter(1, title);
        return query.getSingleResult();
    }
}
