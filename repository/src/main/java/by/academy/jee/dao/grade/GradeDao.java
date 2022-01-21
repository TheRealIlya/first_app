package by.academy.jee.dao.grade;

import by.academy.jee.model.grade.Grade;
import java.util.List;

public interface GradeDao {

    Grade create (Grade grade);

    Grade read(int id);

    Grade update(Grade newGrade);

    boolean delete(Grade grade);

    List<Grade> readAll();
}
