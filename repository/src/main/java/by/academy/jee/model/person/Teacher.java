package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@SecondaryTable(name = "roles", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "id")})
public class Teacher extends Person {
    @ElementCollection
    @CollectionTable(name = "salary", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "value")
    private Map<Integer, Double> salaries = new HashMap<>();

    public Map<Integer, Double> getSalaries() {
        return salaries;
    }

    public Teacher withId(int id) {
        setId(id);
        return this;
    }

    public Teacher withLogin(String login) {
        setLogin(login);
        return this;
    }

    public Teacher withPwd(byte[] pwd) {
        setPwd(pwd);
        return this;
    }

    public Teacher withSalt(byte[] salt) {
        setSalt(salt);
        return this;
    }

    public Teacher withName(String name) {
        setName(name);
        return this;
    }

    public Teacher withAge(int age) {
        setAge(age);
        return this;
    }

    public Teacher withRole(Role role) {
        setRole(role);
        return this;
    }

    public Teacher withSalaries(Map<Integer, Double> salaries) {
        setSalaries(salaries);
        return this;
    }

    public void setSalaries(Map<Integer, Double> salaries) {
        this.salaries = salaries;
    }

    @Override
    public String toString() {
        String mapString = "";
        for (int i = 1; i < 13; i++) {
            mapString += "<br>" + i + " - " + String.format("%.2f", salaries.get(i)).replace(',', '.');
        }
        return super.toString() + "<br><br> Salaries:" + mapString;
    }
}
