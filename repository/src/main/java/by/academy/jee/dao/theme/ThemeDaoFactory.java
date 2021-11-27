package by.academy.jee.dao.theme;

import by.academy.jee.dao.RepositoryType;
import by.academy.jee.exception.DaoException;
import java.io.IOException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.REPOSITORY_PROPERTIES;
import static by.academy.jee.constant.Constant.REPOSITORY_TYPE;

@Slf4j
public class ThemeDaoFactory {

    private static final RepositoryType TYPE;

    static {

        Properties repositoryProperties = new Properties();
        try {
            repositoryProperties.load(Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(REPOSITORY_PROPERTIES));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        TYPE = RepositoryType.getTypeByString(repositoryProperties.getProperty(REPOSITORY_TYPE));
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
