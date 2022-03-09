package by.academy.jee.model.person;

import by.academy.jee.model.AbstractEntity;
import by.academy.jee.model.person.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Person extends AbstractEntity {

    private String login;
    private String password;
    private String name;
    private int age;
    @Column(name = "role_id")
    private Role role;

    @Override
    public String toString() {
        return "User: " + login + " (" + role + ") <br>" +
                "Name: " + name + " <br>" +
                "Age: " + age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @JsonIgnore
    public String getStringForJsp() {
        return toString();
    }
}
