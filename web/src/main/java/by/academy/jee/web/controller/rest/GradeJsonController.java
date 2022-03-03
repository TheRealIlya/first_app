package by.academy.jee.web.controller.rest;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.grade.Grade;
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
import static by.academy.jee.constant.ExceptionConstant.GRADE_UPDATE_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rest/grades", produces = APPLICATION_JSON)
public class GradeJsonController {

    private final Service service;

    @GetMapping
    public List<Grade> getAllGrades() {
        return service.getAllGrades();
    }

    @PostMapping
    public ResponseEntity<?> createGrade(@RequestBody Grade grade) {

        try {
            return ResponseEntity.ok(service.createGrade(grade));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateGrade(@RequestBody Grade grade, @PathVariable int id) {

        try {
            if (grade != null && grade.getId() == id) {
                return ResponseEntity.ok(service.updateGrade(grade));
            } else {
                log.error(GRADE_UPDATE_ERROR);
                return ResponseEntity.badRequest().body(GRADE_UPDATE_ERROR);
            }
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable int id) {

        try {
            return ResponseEntity.ok(service.removeGrade(id));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
