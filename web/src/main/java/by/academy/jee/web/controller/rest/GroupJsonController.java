package by.academy.jee.web.controller.rest;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
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
import static by.academy.jee.constant.ExceptionConstant.GROUP_UPDATE_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rest/groups", produces = APPLICATION_JSON)
public class GroupJsonController {

    private final Service service;

    @GetMapping
    public List<Group> getAllGroups() {
        return service.getAllGroups();
    }

    @GetMapping(value = "/{title}")
    public ResponseEntity<Group> getGroup(@PathVariable String title) {

        try {
            return ResponseEntity.ok(service.getGroup(title));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody Group group) {

        try {
            return ResponseEntity.ok(service.createGroup(group));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateGroup(@RequestBody Group group, @PathVariable int id) {

        try {
            if (group != null && group.getId() == id) {
                return ResponseEntity.ok(service.updateGroup(group));
            } else {
                log.error(GROUP_UPDATE_ERROR);
                return ResponseEntity.badRequest().body(GROUP_UPDATE_ERROR);
            }
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{title}")
    public ResponseEntity<?> deleteGroup(@PathVariable String title) {

        try {
            Group group = service.getGroup(title);
            return ResponseEntity.ok(service.removeGroup(group));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
