package by.academy.jee.web.controller.jsp.common;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.constant.ControllerConstant.ERROR_MESSAGE;
import static by.academy.jee.constant.ControllerConstant.LOGIN_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.SUCCESSFULLY_LOGGED_OUT;
import static by.academy.jee.constant.ControllerConstant.USER_IS_LOGGED_OUT;

@Slf4j
@Controller
public class LogoutController {

    @RequestMapping(value = "/jsp/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView logout(HttpSession httpSession) {

        ModelAndView modelAndView = new ModelAndView();
        httpSession.invalidate();
        log.info(USER_IS_LOGGED_OUT);
        modelAndView.addObject(ERROR_MESSAGE, SUCCESSFULLY_LOGGED_OUT);
        modelAndView.setViewName(LOGIN_JSP_URL);
        return modelAndView;
    }
}
