package by.academy.jee.dao.person.teacher;

import by.academy.jee.dao.person.PersonDao;
import by.academy.jee.database.Database;
import by.academy.jee.model.person.Teacher;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TeacherDaoForMemory implements PersonDao<Teacher> {

    @Override
    public Teacher create(Teacher teacher) {
        Database.getInstance().addTeacher(teacher);
        return teacher;
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
    public Teacher update(Teacher newTeacher) {
        return null; //TODO
    }

    @Override
    public boolean delete(String name) {
        return false; //TODO
    }

    @Override
    public List<Teacher> readAll() {
        return null; //TODO
    }
}
