package by.academy.jee.model.person;

import by.academy.jee.model.grade.Grade;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.role.Role;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@SecondaryTable(name = "roles", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "id")})
public class Student extends Person {

    @OneToMany(mappedBy = "student", cascade = {CascadeType.ALL})
    private List<Grade> grades;
    @ManyToMany
    @JoinTable(
            name = "group_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )

    private List<Group> groups;

    public Student withId(int id) {
        setId(id);
        return this;
    }

    public Student withLogin(String login) {
        setLogin(login);
        return this;
    }

    public Student withPwd(byte[] pwd) {
        setPwd(pwd);
        return this;
    }

    public Student withSalt(byte[] salt) {
        setSalt(salt);
        return this;
    }

    public Student withName(String name) {
        setName(name);
        return this;
    }

    public Student withAge(int age) {
        setAge(age);
        return this;
    }

    public Student withRole(Role role) {
        setRole(role);
        return this;
    }

    public String toString() {
        return getLogin();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String getStringForJsp() {
        String groupString = "";
        for (Group group : groups) {
            groupString += "<br>" + group;
        }
        return super.toString() + "<br><br>Groups:" + groupString;
    }
}
