package by.academy.jee.model.grade;

import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.theme.Theme;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int value;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
}
