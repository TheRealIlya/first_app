package by.academy.jee.web.controller.jsp.teacher;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
import by.academy.jee.web.service.Service;
import by.academy.jee.web.util.SessionUtil;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.constant.ControllerConstant.APPROVE_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.CREATE_GRADE_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.ERROR_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.GRADE_HAS_BEEN_ADDED;
import static by.academy.jee.constant.ControllerConstant.GRADE_STRING;
import static by.academy.jee.constant.ControllerConstant.STUDENT_LOGIN;
import static by.academy.jee.constant.ControllerConstant.THEME_STRING;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/jsp/teacher/createGrade")
public class CreateGradeController {

    private final Service service;

    @GetMapping
    public String redirectFromGetCall() {
        return CREATE_GRADE_JSP_URL;
    }

    @PostMapping
    public ModelAndView createGrade(@RequestParam(STUDENT_LOGIN) String studentLogin,
                                    @RequestParam(THEME_STRING) String themeString,
                                    @RequestParam(GRADE_STRING) String gradeString, HttpSession httpSession) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Group group = SessionUtil.getSessionGroup(httpSession);
            service.createGrade(studentLogin, group, themeString, gradeString);
            log.info(GRADE_HAS_BEEN_ADDED);
            modelAndView.addObject(APPROVE_MESSAGE, GRADE_HAS_BEEN_ADDED);
        } catch (ServiceException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
        }
        modelAndView.setViewName(CREATE_GRADE_JSP_URL);
        return modelAndView;
    }
}
