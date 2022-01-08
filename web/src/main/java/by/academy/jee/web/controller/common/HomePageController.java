package by.academy.jee.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static by.academy.jee.web.constant.Constant.HOME_PAGE_JSP_URL;

@Controller
public class HomePageController {
    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String redirectToJsp() {
        return HOME_PAGE_JSP_URL;
    }
}
