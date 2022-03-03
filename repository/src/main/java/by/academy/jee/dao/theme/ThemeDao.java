package by.academy.jee.dao.theme;

import by.academy.jee.model.theme.Theme;
import java.util.List;

public interface ThemeDao {

    Theme create(Theme theme);

    Theme read(int id);

    Theme read(String title);

    Theme update(Theme newTheme);

    boolean delete(String title);

    List<Theme> readAll();
}
