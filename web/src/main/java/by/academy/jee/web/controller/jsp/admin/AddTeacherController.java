package by.academy.jee.web.controller.jsp.admin;

import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.service.Service;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.constant.ControllerConstant.ADD_TEACHER_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.APPROVE_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.ERROR_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.TEACHER_IS_SUCCESSFULLY_ADDED;

@Slf4j
@RequestMapping(value = "/jsp/admin/addTeacher")
@RequiredArgsConstructor
@Controller
public class AddTeacherController {

    private final Service service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String redirectFromGetCall() {
        return ADD_TEACHER_JSP_URL;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ModelAndView addTeacher(HttpServletRequest req) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Teacher teacher = service.getTeacherFromRequest(req);
            service.createPerson(teacher);
            log.info("Teacher {} is successfully added", teacher.getLogin());
            modelAndView.addObject(APPROVE_MESSAGE, TEACHER_IS_SUCCESSFULLY_ADDED);
        } catch (ServiceException | DaoException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
        }
        modelAndView.setViewName(ADD_TEACHER_JSP_URL);
        return modelAndView;
    }
}
