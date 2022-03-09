package by.academy.jee.web.controller.jsp.common;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static by.academy.jee.constant.CommonConstant.USER;
import static by.academy.jee.constant.ControllerConstant.ALREADY_LOGGED_IN_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.LOGIN_JSP_URL;

@Slf4j
@Controller
@RequestMapping(value = {"/", "/jsp/", "/jsp/login"})
public class LoginController {

    @GetMapping
    public String redirectBeforeLoginPage(HttpSession httpSession) {
        if (httpSession.getAttribute(USER) != null) {
            return ALREADY_LOGGED_IN_JSP_URL;
        }
        return LOGIN_JSP_URL;
    }
}
