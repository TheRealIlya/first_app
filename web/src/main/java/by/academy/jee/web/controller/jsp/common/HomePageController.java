package by.academy.jee.web.controller.jsp.common;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.web.service.Service;
import java.security.Principal;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.constant.ControllerConstant.ERROR_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.HOME_PAGE_JSP_URL;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final Service service;

    @RequestMapping(value = "/jsp/common/home", method = {RequestMethod.GET, RequestMethod.POST})
//    public String redirectToJsp() {
//        return HOME_PAGE_JSP_URL;
//    }
    public ModelAndView homePage(Principal principal, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName(HOME_PAGE_JSP_URL);
            Person user = service.getUserIfExist(principal.getName());
            modelAndView.addObject("user", user);
            session.setAttribute("user", user);
        } catch (ServiceException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
        }
        return modelAndView;
    }
}
