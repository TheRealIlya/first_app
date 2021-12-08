package by.academy.jee.util;

import by.academy.jee.dao.DaoDataSource;
import by.academy.jee.dao.RepositoryType;
import by.academy.jee.exception.DaoException;
import java.io.IOException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.Constant.POSTGRES_DRIVER;
import static by.academy.jee.constant.Constant.POSTGRES_PASSWORD;
import static by.academy.jee.constant.Constant.POSTGRES_URL;
import static by.academy.jee.constant.Constant.POSTGRES_USER;
import static by.academy.jee.constant.Constant.REPOSITORY_PROPERTIES;
import static by.academy.jee.constant.Constant.REPOSITORY_TYPE;

@Slf4j
public class ThreadLocalForRepositoryType extends ThreadLocal<RepositoryType> {

    private static final RepositoryType TYPE;
    private static DaoDataSource dataSource;

    private static volatile ThreadLocalForRepositoryType instance;

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
        getInstance().set(TYPE);
        if (TYPE == RepositoryType.POSTGRES) {
            dataSource = DaoDataSource.getInstance(
                    repositoryProperties.getProperty(POSTGRES_URL),
                    repositoryProperties.getProperty(POSTGRES_USER),
                    repositoryProperties.getProperty(POSTGRES_PASSWORD),
                    repositoryProperties.getProperty(POSTGRES_DRIVER));
        }
    }

    private ThreadLocalForRepositoryType() {
        //singleton
    }

    public static ThreadLocalForRepositoryType getInstance() {

        if (instance == null) {
            synchronized (ThreadLocalForRepositoryType.class) {
                if (instance == null) {
                    instance = new ThreadLocalForRepositoryType();
                }
            }
        }
        return instance;
    }

    public DaoDataSource getDataSource() {
        return dataSource;
    }
}
