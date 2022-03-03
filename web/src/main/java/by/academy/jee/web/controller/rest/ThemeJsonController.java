package by.academy.jee.web.controller.rest;


import by.academy.jee.exception.ServiceException;
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
import static by.academy.jee.constant.ControllerConstant.APPLICATION_JSON;
import static by.academy.jee.constant.ExceptionConstant.THEME_UPDATE_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rest/themes", produces = APPLICATION_JSON)
public class ThemeJsonController {

    private final Service service;

    @GetMapping
    public List<Theme> getAllThemes() {
        return service.getAllThemes();
    }

    @GetMapping(value = "/{title}")
    public ResponseEntity<Theme> getTheme(@PathVariable String title) {

        try {
            return ResponseEntity.ok(service.getTheme(title));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTheme(@RequestBody Theme theme) {

        try {
            return ResponseEntity.ok(service.createTheme(theme));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTheme(@RequestBody Theme theme, @PathVariable int id) {

        try {
            if (theme != null && theme.getId() == id) {
                return ResponseEntity.ok(service.updateTheme(theme));
            } else {
                log.error(THEME_UPDATE_ERROR);
                return ResponseEntity.badRequest().body(THEME_UPDATE_ERROR);
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
            return ResponseEntity.ok(service.removeTheme(theme));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
