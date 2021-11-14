package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity(name = "users")
public class Admin extends Person {

    public Admin withId(int id) {
        setId(id);
        return this;
    }

    public Admin withLogin(String login) {
        setLogin(login);
        return this;
    }

    public Admin withPwd(byte[] pwd) {
        setPwd(pwd);
        return this;
    }

    public Admin withSalt(byte[] salt) {
        setSalt(salt);
        return this;
    }

    public Admin withName(String name) {
        setName(name);
        return this;
    }

    public Admin withAge(int age) {
        setAge(age);
        return this;
    }

    public Admin withRole(Role role) {
        setRole(role);
        return this;
    }

    public String toString() {
        return super.toString();
    }
}
