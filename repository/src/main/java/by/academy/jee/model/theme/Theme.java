package by.academy.jee.model.theme;

import by.academy.jee.model.AbstractEntity;
import by.academy.jee.model.grade.Grade;
import by.academy.jee.model.group.Group;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Theme extends AbstractEntity {

    private String title;
    @ManyToMany(mappedBy = "themes", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @EqualsAndHashCode.Exclude
    private List<Group> groups;
    @OneToMany(mappedBy = "theme", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @EqualsAndHashCode.Exclude
    private List<Grade> grades;

    @Override
    public String toString() {
        return title;
    }
}
