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
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.CREATE_GRADE_JSP_URL;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/jsp/createGrade")
public class CreateGradeController {

    private final Service service;

    @GetMapping
    public String redirectFromGetCall() {
        return CREATE_GRADE_JSP_URL;
    }

    @PostMapping
    public ModelAndView createGrade(@RequestParam("studentLogin") String studentLogin,
                                    @RequestParam("themeString") String themeString,
                                    @RequestParam("gradeString") String gradeString, HttpSession httpSession) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Group group = SessionUtil.getSessionGroup(httpSession);
            service.createGrade(studentLogin, group, themeString, gradeString);
            log.info("Grade has been added");
            modelAndView.addObject(APPROVE_MESSAGE, "Grade has been added");
        } catch (ServiceException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
        }
        modelAndView.setViewName(CREATE_GRADE_JSP_URL);
        return modelAndView;
    }
}
