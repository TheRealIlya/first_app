package by.academy.jee.web.controller.rest;


import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.theme.Theme;
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
@RequestMapping(value = "/rest/themes", produces = "application/json")
public class ThemeJsonController {

    private final Service service;

    @GetMapping
    public List<Theme> getAllThemes() {
        List<Theme> themes = service.getAllThemes();
        for (Theme theme : themes) {
            prepareThemeForJson(theme);
        }
        return themes;
    }

    @GetMapping(value = "/{title}")
    public ResponseEntity<Theme> getTheme(@PathVariable String title) {

        try {
            Theme theme = service.getTheme(title);
            return ResponseEntity.ok(prepareThemeForJson(theme));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTheme(@RequestBody Theme theme) {

        try {
            Theme themeFromService = service.createTheme(theme);
            prepareThemeForJson(themeFromService);
            return ResponseEntity.ok(themeFromService);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTheme(@RequestBody Theme theme, @PathVariable int id) {

        try {
            if (theme != null && theme.getId() == id) {
                prepareThemeForJson(theme);
                return ResponseEntity.ok(service.updateTheme(theme));
            } else {
                log.error("Error - Theme id must be equal with id in path");
                return ResponseEntity.badRequest().body("Error - Theme id must be equal with id in path");
            }
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{title}")
    public ResponseEntity<?> deleteTheme(@PathVariable String title) {

        try {
            Theme theme = service.getTheme(title);
            prepareThemeForJson(theme);
            return ResponseEntity.ok(service.removeTheme(theme));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Theme prepareThemeForJson(Theme theme) {

        theme.setGrades(null);
        if (theme.getGroups() != null) {
            for (Group group : theme.getGroups()) {
                group.setTeacher(null);
                group.setGrades(null);
                group.setThemes(null);
                group.setStudents(null);
            }
        }
        return theme;
    }
}
