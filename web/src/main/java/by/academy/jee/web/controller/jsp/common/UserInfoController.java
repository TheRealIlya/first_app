package by.academy.jee.web.controller.jsp.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static by.academy.jee.web.constant.Constant.USER_INFO_JSP_URL;

@Controller
public class UserInfoController {
    @RequestMapping(value = "/userInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public String redirectToJsp() {
        return USER_INFO_JSP_URL;
    }
}
