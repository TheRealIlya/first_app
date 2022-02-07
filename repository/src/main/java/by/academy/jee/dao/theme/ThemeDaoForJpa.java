package by.academy.jee.dao.theme;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.model.theme.Theme;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import static by.academy.jee.constant.JpaQueryConstant.GET_ALL_THEMES;
import static by.academy.jee.constant.JpaQueryConstant.GET_THEME_BY_TITLE;

@Component
public class ThemeDaoForJpa extends CommonDaoForJpa<Theme> implements ThemeDao {

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
        TypedQuery<Theme> query = em.createQuery(GET_ALL_THEMES, Theme.class);
        return query.getResultList();
    }

    private Theme getThemeByTitle(String title, EntityManager em) {

        TypedQuery<Theme> query = em.createQuery(GET_THEME_BY_TITLE, Theme.class);
        query.setParameter(1, title);
        return query.getSingleResult();
    }
}
