package by.academy.jee.model.person;

import by.academy.jee.model.person.role.Role;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class PersonDto {

    private Integer id;
    private String login;
    private String password;
    private String name;
    private int age;
    private Role role;
    private Map<Integer, Double> salaries;
}
