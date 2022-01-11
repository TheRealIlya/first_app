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
import static by.academy.jee.web.constant.Constant.APPROVE_MESSAGE;
import static by.academy.jee.web.constant.Constant.CHANGE_GROUP_JSP_URL;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.TEACHER_MENU_JSP_URL;

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
    public ModelAndView changeGroup(HttpSession httpSession, @RequestParam("title") String newGroupTitle) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Group oldGroup = SessionUtil.getSessionGroup(httpSession);
            Person person = SessionUtil.getSessionUser(httpSession);
            service.checkIsNotATeacher(person);
            Teacher teacher = (Teacher) person;
            service.changeGroup(oldGroup, newGroupTitle, teacher);
            modelAndView.addObject(APPROVE_MESSAGE, "Your group has been changed");
            modelAndView.setViewName(TEACHER_MENU_JSP_URL);
        } catch (ServiceException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
            modelAndView.setViewName(CHANGE_GROUP_JSP_URL);
        }
        return modelAndView;
    }
}
