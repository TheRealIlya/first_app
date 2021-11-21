package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    @Column(name = "password")
    private byte[] pwd;
    private byte[] salt;
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
}
