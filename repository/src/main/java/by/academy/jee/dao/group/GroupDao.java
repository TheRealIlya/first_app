package by.academy.jee.dao.group;

import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Teacher;
import java.util.List;

public interface GroupDao {

    Group create(Group group);

    Group read(int id);

    Group read(String title);

    Group read(Teacher teacher);

    Group update(Group newGroup);

    boolean delete(String title);

    List<Group> readAll();
}
