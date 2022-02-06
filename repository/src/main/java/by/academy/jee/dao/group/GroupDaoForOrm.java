package by.academy.jee.dao.group;

import by.academy.jee.dao.common.CommonDaoForOrm;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Teacher;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

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
        TypedQuery<Group> query = em.createQuery("from Group", Group.class);
        return query.getResultList();
    }

    private Group getGroupByTeacher(Teacher teacher) {

        TypedQuery<Group> query = em.createQuery("from Group g where g.teacher = ?1", Group.class);
        query.setParameter(1, teacher);
        return query.getSingleResult();
    }

    private Group getGroupByTitle(String title) {

        TypedQuery<Group> query = em.createQuery("from Group g where g.title = ?1", Group.class);
        query.setParameter(1, title);
        return query.getSingleResult();
    }
}
