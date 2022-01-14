package by.academy.jee.web.controller.rest;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.role.Role;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rest/admins", produces = "application/json")
public class AdminJsonController {

    private final Service service;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return service.getAllAdmins();
    }

    @GetMapping(value = "/{login}")
    public ResponseEntity<?> getAdmin(@PathVariable String login) {

        try {
            Person person = service.getUserIfExist(login);
            service.checkIsNotAnAdmin(person);
            return ResponseEntity.ok(person);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {
        try {
            return ResponseEntity.ok(service.createPerson(admin));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin, @PathVariable int id) {

        try {
            if (admin != null && admin.getId() == id) {
                return ResponseEntity.ok(service.updateAdmin(admin));
            } else {
                log.error("Error - Admin's id must be equal with id in path");
                return ResponseEntity.badRequest().body("Error - Admin's id must be equal with id in path");
            }
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{login}")
    public ResponseEntity<?> deleteAdmin(@PathVariable String login) {

        try {
            Person person = service.getUserIfExist(login);
            if (Role.ADMIN.equals(person.getRole())) {
                return ResponseEntity.ok(service.removeUser(person));
            }
            log.error("Error - no admin with login" + login);
            return ResponseEntity.notFound().build();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
