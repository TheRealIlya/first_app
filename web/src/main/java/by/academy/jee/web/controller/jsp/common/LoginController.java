package by.academy.jee.web.controller.jsp.common;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.web.service.Service;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.constant.ControllerConstant.ALREADY_LOGGED_IN_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.ERROR_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.LOGIN_JSP_URL;
import static by.academy.jee.constant.CommonConstant.PASSWORD;
import static by.academy.jee.constant.CommonConstant.USER;
import static by.academy.jee.constant.CommonConstant.USER_NAME;

@Slf4j
@Controller
@RequestMapping(value = {"/", "/jsp/", "/jsp/login"})
@RequiredArgsConstructor
public class LoginController {

    private final Service service;

    @GetMapping
    public String redirectBeforeLoginPage(HttpSession httpSession) {
        if (httpSession.getAttribute(USER) != null) {
            return ALREADY_LOGGED_IN_JSP_URL;
        }
        return LOGIN_JSP_URL;
    }

    @PostMapping
    public ModelAndView login(@RequestParam(USER_NAME) String userName, @RequestParam(PASSWORD) String password,
                              HttpSession httpSession) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Person user = service.getUserIfExist(userName);
            service.checkPassword(password, user);
            httpSession.setAttribute(USER, user);
            String role = user.getRole().toString();
            log.info("User {} is successfully authorised, role - {}", user.getLogin(), role);
            String menuUrl = service.getMenuUrlAfterLogin(role);
            modelAndView.setViewName(menuUrl);
        } catch (ServiceException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
            modelAndView.setViewName(LOGIN_JSP_URL);
        }
        return modelAndView;
    }
}
