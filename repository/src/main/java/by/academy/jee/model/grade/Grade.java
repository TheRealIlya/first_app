package by.academy.jee.model.grade;

import by.academy.jee.model.AbstractEntity;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Student;
import by.academy.jee.model.theme.Theme;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Grade extends AbstractEntity {

    private int value;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "theme_id")
    private Theme theme;

    public Grade withId(int id) {
        setId(id);
        return this;
    }

    public Grade withValue(int value) {
        setValue(value);
        return this;
    }

    public Grade withStudent(Student student) {
        setStudent(student);
        return this;
    }

    public Grade withGroup(Group group) {
        setGroup(group);
        return this;
    }

    public Grade withTheme(Theme theme) {
        setTheme(theme);
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
