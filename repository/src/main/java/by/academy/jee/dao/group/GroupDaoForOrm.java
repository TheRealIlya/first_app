package by.academy.jee.dao.group;

import by.academy.jee.dao.common.CommonDaoForOrm;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Teacher;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import static by.academy.jee.constant.JpaQueryConstant.GET_ALL_GROUPS;
import static by.academy.jee.constant.JpaQueryConstant.GET_GROUP_BY_TEACHER;
import static by.academy.jee.constant.JpaQueryConstant.GET_GROUP_BY_TITLE;

@Repository
public class GroupDaoForOrm extends CommonDaoForOrm<Group> implements GroupDao {

    @Override
    public Group read(String title) {
        return getGroupByTitle(title);
    }

    @Override
    public Group read(Teacher teacher) {
        return getGroupByTeacher(teacher);
    }

    @Override
    public List<Group> readAll() {
        return getAllGroups();
    }

    private List<Group> getAllGroups() {
        TypedQuery<Group> query = em.createQuery(GET_ALL_GROUPS, Group.class);
        return query.getResultList();
    }

    private Group getGroupByTeacher(Teacher teacher) {

        TypedQuery<Group> query = em.createQuery(GET_GROUP_BY_TEACHER, Group.class);
        query.setParameter(1, teacher);
        return query.getSingleResult();
    }

    private Group getGroupByTitle(String title) {

        TypedQuery<Group> query = em.createQuery(GET_GROUP_BY_TITLE, Group.class);
        query.setParameter(1, title);
        return query.getSingleResult();
    }
}
