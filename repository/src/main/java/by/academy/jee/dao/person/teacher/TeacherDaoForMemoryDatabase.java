package by.academy.jee.dao.person.teacher;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Teacher;

import java.util.List;

public class TeacherDaoForMemoryDatabase implements PersonDao<Teacher> {

    private static volatile TeacherDaoForMemoryDatabase instance;

    private TeacherDaoForMemoryDatabase() {
        //singleton
    }

    public static TeacherDaoForMemoryDatabase getInstance() {
        if (instance == null) {
            synchronized (TeacherDaoForMemoryDatabase.class) {
                if (instance == null) {
                    instance = new TeacherDaoForMemoryDatabase();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean create(Teacher teacher) {
        Database.getInstance().addTeacher(teacher);
        return true;
    }

    @Override
    public Teacher read(int id) {
        return null; //TODO
    }

    @Override
    public Teacher read(String name) {
        return Database.getInstance().getTeacher(name);
    }

    @Override
    public boolean update(Teacher newTeacher) {
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
