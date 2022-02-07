package by.academy.jee.web.controller.rest;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.PersonDto;
import by.academy.jee.web.service.Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static by.academy.jee.constant.ControllerConstant.APPLICATION_JSON;
import static by.academy.jee.constant.ExceptionConstant.PERSON_UPDATE_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rest/persons", produces = APPLICATION_JSON)
public class PersonJsonController {

    private final Service service;

    @GetMapping
    public List<Person> getAllPersons() {
        return service.getAllPersons();
    }

    @GetMapping(value = "/{login}")
    public ResponseEntity<Person> getPerson(@PathVariable String login) {

        try {
            Person person = service.getUserIfExist(login);
            return ResponseEntity.ok(person);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody PersonDto personDto) {
        try {
            Person person = service.getPersonFromDto(personDto);
            return ResponseEntity.ok(service.createPerson(person));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@RequestBody PersonDto personDto, @PathVariable int id) {

        try {
            if (personDto != null && personDto.getId() == id) {
                Person person = service.getPersonFromDto(personDto);
                return ResponseEntity.ok(service.updatePerson(person));
            } else {
                log.error(PERSON_UPDATE_ERROR);
                return ResponseEntity.badRequest().body(PERSON_UPDATE_ERROR);
            }
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{login}")
    public ResponseEntity<?> deletePerson(@PathVariable String login) {

        try {
            Person person = service.getUserIfExist(login);
            return ResponseEntity.ok(service.removeUser(person));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
