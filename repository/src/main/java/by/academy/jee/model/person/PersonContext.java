package by.academy.jee.model.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class PersonContext {

    private Integer id;
    private String login;
    private byte[] pwd;
    private byte[] salt;
    private String name;
    private int age;
}
