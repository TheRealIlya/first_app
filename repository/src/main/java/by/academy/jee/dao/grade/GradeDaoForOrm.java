package by.academy.jee.dao.grade;

import by.academy.jee.dao.common.CommonDaoForOrm;
import by.academy.jee.model.grade.Grade;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class GradeDaoForOrm extends CommonDaoForOrm<Grade> implements GradeDao {

    @Override
    public Grade read(String name) {
        throw new UnsupportedOperationException();
    }

    @Override

    public Grade update(Grade newGrade) {

        Query query = em.createQuery("update Grade set value = ?1 where id = ?2");
        query.setParameter(1, newGrade.getValue());
        query.setParameter(2, newGrade.getId());
        query.executeUpdate();
        return read(newGrade.getId());
    }

    @Override
    public boolean delete(Grade grade) {
        em.remove(grade);
        return true;
    }

    @Override
    public List<Grade> readAll() {
        return getAllGrades();
    }

    private List<Grade> getAllGrades() {
        TypedQuery<Grade> query = em.createQuery("from Grade", Grade.class);
        return query.getResultList();
    }
}
