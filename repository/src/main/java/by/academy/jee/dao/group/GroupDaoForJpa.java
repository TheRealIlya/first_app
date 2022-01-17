package by.academy.jee.dao.group;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Teacher;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class GroupDaoForJpa extends CommonDaoForJpa<Group> implements GroupDao {

    @Override
    public Group read(String title) {
        EntityManager em = emHelper.get();
        return getGroupByTitle(title, em);
    }

    @Override
    public Group read(Teacher teacher) {
        EntityManager em = emHelper.get();
        return getGroupByTeacher(teacher, em);
    }

    @Override
    public List<Group> readAll() {
        EntityManager em = emHelper.get();
        return getAllGroups(em);
    }

    private List<Group> getAllGroups(EntityManager em) {
        TypedQuery<Group> query = em.createQuery("from Group", Group.class);
        return query.getResultList();
    }

    private Group getGroupByTeacher(Teacher teacher, EntityManager em) {

        TypedQuery<Group> query = em.createQuery("from Group g where g.teacher = ?1", Group.class);
        query.setParameter(1, teacher);
        return query.getSingleResult();
    }

    private Group getGroupByTitle(String title, EntityManager em) {

        TypedQuery<Group> query = em.createQuery("from Group g where g.title = ?1", Group.class);
        query.setParameter(1, title);
        return query.getSingleResult();
    }
}
