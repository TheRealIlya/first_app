package by.academy.jee.dao.theme;

import by.academy.jee.dao.common.CommonDaoForOrm;
import by.academy.jee.model.theme.Theme;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDaoForOrm extends CommonDaoForOrm<Theme> implements ThemeDao {

    @Override
    public Theme read(String title) {
        return getThemeByTitle(title);
    }

    @Override
    public List<Theme> readAll() {
        return getAllThemes();
    }

    private List<Theme> getAllThemes() {
        TypedQuery<Theme> query = em.createQuery("from Theme", Theme.class);
        return query.getResultList();
    }

    private Theme getThemeByTitle(String title) {

        TypedQuery<Theme> query = em.createQuery("from Theme t where t.title = ?1", Theme.class);
        query.setParameter(1, title);
        return query.getSingleResult();
    }
}
