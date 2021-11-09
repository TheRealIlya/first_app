package by.academy.jee.dao.person;

import by.academy.jee.dao.DaoDataSource;
import by.academy.jee.dao.person.admin.AdminDaoForMemoryDatabase;
import by.academy.jee.dao.person.admin.AdminDaoForPostgres;
import by.academy.jee.dao.person.teacher.TeacherDaoForMemoryDatabase;
import by.academy.jee.dao.person.teacher.TeacherDaoForPostgres;
import by.academy.jee.exception.PersonDaoException;
import by.academy.jee.model.person.role.Role;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static by.academy.jee.constant.Constant.ADMIN;
import static by.academy.jee.constant.Constant.POSTGRES_DRIVER;
import static by.academy.jee.constant.Constant.POSTGRES_PASSWORD;
import static by.academy.jee.constant.Constant.POSTGRES_URL;
import static by.academy.jee.constant.Constant.POSTGRES_USER;
import static by.academy.jee.constant.Constant.REPOSITORY_PROPERTIES;
import static by.academy.jee.constant.Constant.REPOSITORY_TYPE;
import static by.academy.jee.constant.Constant.STUDENT;
import static by.academy.jee.constant.Constant.TEACHER;


public class PersonDaoFactory {

    private static final Logger log = LoggerFactory.getLogger(PersonDaoFactory.class);
    private static final RepositoryType TYPE;
    private static DaoDataSource dataSource;

    static {

        Properties repositoryProperties = new Properties();
        try {
            repositoryProperties.load(Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(REPOSITORY_PROPERTIES));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new PersonDaoException(e.getMessage(), e);
        }
        TYPE = RepositoryType.getTypeByString(repositoryProperties.getProperty(REPOSITORY_TYPE));
        if (TYPE == RepositoryType.POSTGRES) {
            dataSource = DaoDataSource.getInstance(
                    repositoryProperties.getProperty(POSTGRES_URL),
                    repositoryProperties.getProperty(POSTGRES_USER),
                    repositoryProperties.getProperty(POSTGRES_PASSWORD),
                    repositoryProperties.getProperty(POSTGRES_DRIVER));
        }
    }

    private PersonDaoFactory() {
        //factory
    }

    public static PersonDao getPersonDao(Role role) {

        switch (TYPE) {
            case MEMORY:
                return getMemoryDao(role);
            case POSTGRES:
            default:
                return getPostgresDao(role);
        }
    }

    private static PersonDao getMemoryDao(Role role) {

        switch (role.toString()) {
            case ADMIN:
                return AdminDaoForMemoryDatabase.getInstance();
            case TEACHER:
                return TeacherDaoForMemoryDatabase.getInstance();
            case STUDENT:
            default:
                return null; //TODO
        }
    }

    private static PersonDao getPostgresDao(Role role) {

        switch (role.toString()) {
            case ADMIN:
                return AdminDaoForPostgres.getInstance(dataSource);
            case TEACHER:
                return TeacherDaoForPostgres.getInstance(dataSource);
            case STUDENT:
            default:
                return null; //TODO
        }
    }
}
