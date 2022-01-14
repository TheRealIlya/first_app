package by.academy.jee.dao.theme;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.MyNoResultException;
import by.academy.jee.model.theme.Theme;
import by.academy.jee.util.ThreadLocalForEntityManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class ThemeDaoForJpa extends CommonDaoForJpa<Theme> implements ThemeDao {

    private final ThreadLocalForEntityManager emHelper = ThreadLocalForEntityManager.getInstance();

    @Override
    public Theme read(String title) {

        EntityManager em = emHelper.get();
        try {
            return getThemeByTitle(title, em);
        } catch (NoResultException e) {
            throw new MyNoResultException(e.getMessage());
        } catch (Exception e) {
            throw new DaoException("No theme with such title");
        }
    }

    @Override
    public List<Theme> readAll() {

        EntityManager em = emHelper.get();
        try {
            return getAllThemes(em);
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    private List<Theme> getAllThemes(EntityManager em) {
        TypedQuery<Theme> query = em.createQuery("from Theme", Theme.class);
        return query.getResultList();
    }

    private Theme getThemeByTitle(String title, EntityManager em) {

        TypedQuery<Theme> query = em.createQuery("from Theme t where t.title = ?1", Theme.class);
        query.setParameter(1, title);
        return query.getSingleResult();
    }
}
