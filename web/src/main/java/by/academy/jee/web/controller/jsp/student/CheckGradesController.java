package by.academy.jee.web.controller.jsp.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static by.academy.jee.constant.ControllerConstant.CHECK_GRADES_JSP_URL;

@Controller
public class CheckGradesController {
    @RequestMapping(value = "/jsp/checkGrades", method = {RequestMethod.GET, RequestMethod.POST})
    public String redirectToJsp() {
        return CHECK_GRADES_JSP_URL;
    }
}
