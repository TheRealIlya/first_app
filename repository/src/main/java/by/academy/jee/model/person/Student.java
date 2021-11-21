package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@SecondaryTable(name = "roles", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "id")})
public class Student extends Person {

    @Transient
    private Map<Integer, Integer> marks;

    public Map<Integer, Integer> getMarks() {
        return marks;
    }

    public void setMarks(Map<Integer, Integer> marks) {
        this.marks = marks;
    }

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
        return super.toString();
    }
}
