package by.academy.jee.web.controller.jsp.teacher;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.service.Service;
import by.academy.jee.web.util.SessionUtil;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.constant.CommonConstant.TITLE;
import static by.academy.jee.constant.ControllerConstant.APPROVE_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.CHANGE_GROUP_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.ERROR_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.GROUP_HAS_BEEN_CHANGED;
import static by.academy.jee.constant.ControllerConstant.TEACHER_MENU_JSP_URL;

@Controller
@RequestMapping(value = "/jsp/changeGroup")
@RequiredArgsConstructor
public class ChangeGroupController {

    private final Service service;

    @GetMapping
    public String redirectFromGetCall() {
        return CHANGE_GROUP_JSP_URL;
    }

    @PostMapping
    public ModelAndView changeGroup(HttpSession httpSession, @RequestParam(TITLE) String newGroupTitle) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Group oldGroup = SessionUtil.getSessionGroup(httpSession);
            Person person = SessionUtil.getSessionUser(httpSession);
            service.checkIsNotATeacher(person);
            Teacher teacher = (Teacher) person;
            service.changeGroup(oldGroup, newGroupTitle, teacher);
            modelAndView.addObject(APPROVE_MESSAGE, GROUP_HAS_BEEN_CHANGED);
            modelAndView.setViewName(TEACHER_MENU_JSP_URL);
        } catch (ServiceException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
            modelAndView.setViewName(CHANGE_GROUP_JSP_URL);
        }
        return modelAndView;
    }
}
