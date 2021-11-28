package by.academy.jee.dao.group;

import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Teacher;

public interface GroupDao {

    Group create(Group group);

    Group read(int id);

    Group read(String title);

    Group read(Teacher teacher);

    boolean update(Group newGroup);
}
