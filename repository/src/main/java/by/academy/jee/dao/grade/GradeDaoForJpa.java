package by.academy.jee.dao.grade;

import by.academy.jee.dao.common.CommonDaoForJpa;
import by.academy.jee.model.grade.Grade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class GradeDaoForJpa extends CommonDaoForJpa<Grade> implements GradeDao {

    @Override
    public Grade read(String name) {
        throw new UnsupportedOperationException();
    }

    @Override

    public Grade update(Grade newGrade) {
        EntityManager em = emHelper.get();
        Query query = em.createQuery("update Grade set value = ?1 where id = ?2");
        query.setParameter(1, newGrade.getValue());
        query.setParameter(2, newGrade.getId());
        query.executeUpdate();
        return read(newGrade.getId());
    }

    @Override
    public boolean delete(Grade grade) {
        EntityManager em = emHelper.get();
        em.remove(grade);
        return true;
    }

    @Override
    public List<Grade> readAll() {
        EntityManager em = emHelper.get();
        return getAllGrades(em);
    }

    private List<Grade> getAllGrades(EntityManager em) {
        TypedQuery<Grade> query = em.createQuery("from Grade", Grade.class);
        return query.getResultList();
    }
}
