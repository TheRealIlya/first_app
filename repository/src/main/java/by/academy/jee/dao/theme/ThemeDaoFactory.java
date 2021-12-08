package by.academy.jee.dao.theme;

import by.academy.jee.dao.DaoDataSource;
import by.academy.jee.dao.RepositoryType;
import by.academy.jee.util.ThreadLocalForRepositoryType;

public class ThemeDaoFactory {

    private static DaoDataSource dataSource;
    private static final RepositoryType TYPE;
    private static final ThreadLocalForRepositoryType repositoryTypeHelper = ThreadLocalForRepositoryType.getInstance();

    static {

        TYPE = repositoryTypeHelper.get();
        if (TYPE == RepositoryType.POSTGRES) {
            dataSource = repositoryTypeHelper.getDataSource();
        }
    }

    private ThemeDaoFactory() {
        //factory
    }

    public static ThemeDao getThemeDao() {

        switch (TYPE) {
            case MEMORY:

            case POSTGRES:

            case JPA:
            default:
                return ThemeDaoForJpa.getInstance();
        }
    }
}
