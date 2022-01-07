package by.academy.jee.util;

import by.academy.jee.dao.DaoDataSource;
import by.academy.jee.dao.RepositoryType;
import by.academy.jee.exception.DaoException;
import java.io.IOException;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import static by.academy.jee.constant.Constant.REPOSITORY_PROPERTIES;
import static by.academy.jee.constant.Constant.REPOSITORY_TYPE;

@Slf4j
@Component
@PropertySource("classpath:repository.properties")
public class ThreadLocalForRepositoryType extends ThreadLocal<RepositoryType> {

    private final String type;
    //private final RepositoryType TYPE = RepositoryType.getTypeByString(type);
    private final DaoDataSource dataSource;

    //private static volatile ThreadLocalForRepositoryType instance;

    public ThreadLocalForRepositoryType(@Value("${repository.type}") String type, DaoDataSource dataSource) {
        this.type = type;
        this.dataSource = dataSource;
    }

//    static {
//
//        Properties repositoryProperties = new Properties();
//        try {
//            repositoryProperties.load(Thread.currentThread()
//                    .getContextClassLoader().getResourceAsStream(REPOSITORY_PROPERTIES));
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//            throw new DaoException(e.getMessage(), e);
//        }
//        TYPE = RepositoryType.getTypeByString(repositoryProperties.getProperty(REPOSITORY_TYPE));
//        getInstance().set(TYPE);
//    }
//
//    private ThreadLocalForRepositoryType() {
//        //singleton
//    }
//
//    public static ThreadLocalForRepositoryType getInstance() {
//
//        if (instance == null) {
//            synchronized (ThreadLocalForRepositoryType.class) {
//                if (instance == null) {
//                    instance = new ThreadLocalForRepositoryType();
//                }
//            }
//        }
//        return instance;
//    }

    public DaoDataSource getDataSource() {
        return dataSource;
    }

    public String getType() {
        return type;
    }
}
