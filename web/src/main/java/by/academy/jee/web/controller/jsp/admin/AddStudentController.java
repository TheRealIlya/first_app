package by.academy.jee.web.controller.jsp.admin;

import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Student;
import by.academy.jee.web.service.Service;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.web.constant.Constant.ADD_STUDENT_JSP_URL;
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.STUDENT_IS_SUCCESSFULLY_ADDED;

@Slf4j
@Controller
@RequestMapping(value = "/jsp/addStudent")
@RequiredArgsConstructor
public class AddStudentController {

    private final Service service;

    @GetMapping
    public String redirectFromGetCall() {
        return ADD_STUDENT_JSP_URL;
    }

    @PostMapping
    public ModelAndView addStudent(HttpServletRequest req) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Student student = service.getStudentFromRequestWithoutId(req);
            service.createPerson(student);
            log.info("Student {} is successfully added", student.getLogin());
            modelAndView.addObject(APPROVE_MESSAGE, STUDENT_IS_SUCCESSFULLY_ADDED);
        } catch (ServiceException | DaoException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
        }
        modelAndView.setViewName(ADD_STUDENT_JSP_URL);
        return modelAndView;
    }
}
