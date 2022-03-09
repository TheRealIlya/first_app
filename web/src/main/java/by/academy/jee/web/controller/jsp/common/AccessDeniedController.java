package by.academy.jee.web.controller.jsp.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping(value = "/jsp/common/accessDenied", method = {RequestMethod.GET, RequestMethod.POST})
public class AccessDeniedController {

    @GetMapping
    public String accessDenied() {
        log.error("Error - access denied");
        return "/common/denied";
    }
}
