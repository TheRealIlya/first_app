package by.academy.jee.web.controller.jsp.teacher;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.group.Group;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.service.Service;
import by.academy.jee.web.util.SessionUtil;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.web.constant.Constant.ERROR_MESSAGE;
import static by.academy.jee.web.constant.Constant.GROUP_INFO_ERROR_PAGE_JSP_URL;
import static by.academy.jee.web.constant.Constant.GROUP_INFO_JSP_URL;

@Slf4j
@RequiredArgsConstructor
@Controller
public class GroupInfoController {

    private final Service service;

    @RequestMapping(value = "/jsp/groupInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getGroupInfo(HttpSession httpSession) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Person person = SessionUtil.getSessionUser(httpSession);
            service.checkIsNotATeacher(person);
            Teacher teacher = (Teacher) person;
            Group group = service.getGroupByTeacher(teacher);
            httpSession.setAttribute("group", group);
            modelAndView.setViewName(GROUP_INFO_JSP_URL);
        } catch (ServiceException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
            modelAndView.setViewName(GROUP_INFO_ERROR_PAGE_JSP_URL);
        }
        return modelAndView;
    }
}
