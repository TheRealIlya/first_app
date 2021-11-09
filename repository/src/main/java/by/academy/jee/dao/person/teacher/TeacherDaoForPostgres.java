package by.academy.jee.dao.person.teacher;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.model.person.Teacher;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeacherDaoForPostgres implements PersonDao<Teacher> {

    private static final Logger log = LoggerFactory.getLogger(TeacherDaoForPostgres.class);

    private final DataSource dataSource;

    private static volatile TeacherDaoForPostgres instance;

    private TeacherDaoForPostgres(DataSource dataSource) {
        //singleton
        this.dataSource = dataSource;
    }

    public static TeacherDaoForPostgres getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (TeacherDaoForPostgres.class) {
                if (instance == null) {
                    instance = new TeacherDaoForPostgres(dataSource);
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Teacher teacher) {
        return false; //TODO
    }

    @Override
    public Teacher read(int id) {
        return null; //TODO
    }

    @Override
    public Teacher read(String name) {
        return null; //TODO
    }

    @Override
    public boolean update(Teacher newT) {
        return false; //TODO
    }

    @Override
    public boolean delete(int id) {
        return false; //TODO
    }

    @Override
    public List<Teacher> readAll() {
        return null; //TODO
    }
}
