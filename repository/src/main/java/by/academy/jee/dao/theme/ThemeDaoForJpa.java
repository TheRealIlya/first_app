package by.academy.jee.dao.theme;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.model.theme.Theme;
import by.academy.jee.util.ThreadLocalForEntityManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class ThemeDaoForJpa extends CommonDaoForJpa<Theme> implements ThemeDao {

    private final ThreadLocalForEntityManager emHelper = ThreadLocalForEntityManager.getInstance();

    @Override
    public Theme read(String title) {
        EntityManager em = emHelper.get();
        return getThemeByTitle(title, em);
    }

    @Override
    public List<Theme> readAll() {
        EntityManager em = emHelper.get();
        return getAllThemes(em);
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
