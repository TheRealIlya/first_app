package by.academy.jee.web.controller.jsp.admin;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Person;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.service.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import static by.academy.jee.constant.ControllerConstant.AVERAGE_SALARY_CALCULATED;
import static by.academy.jee.constant.ControllerConstant.AVG_SALARY_JSP_URL;
import static by.academy.jee.constant.ControllerConstant.ERROR_MESSAGE;
import static by.academy.jee.constant.CommonConstant.FIRST_MONTH;
import static by.academy.jee.constant.CommonConstant.LAST_MONTH;
import static by.academy.jee.constant.CommonConstant.LOGIN;
import static by.academy.jee.constant.CommonConstant.RESULT;

@Slf4j
@RequestMapping(value = "/jsp/avgSalary")
@Controller
@RequiredArgsConstructor
public class AverageSalaryController {

    private final Service service;

    @GetMapping
    public String redirectFromGetCall() {
        return AVG_SALARY_JSP_URL;
    }

    @PostMapping
    public ModelAndView getAverageSalary(@RequestParam(LOGIN) String login, @RequestParam(FIRST_MONTH) String firstMonth,
                                         @RequestParam(LAST_MONTH) String lastMonth) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            Person person = service.getUserIfExist(login);
            service.checkIsNotATeacher(person);
            Teacher teacher = (Teacher) person;
            String averageSalaryString = service.getAverageSalaryByMonths(teacher, firstMonth, lastMonth);
            log.info(AVERAGE_SALARY_CALCULATED);
            String result = "Average salary: " + averageSalaryString;
            modelAndView.addObject(RESULT, result);
        } catch (ServiceException e) {
            modelAndView.addObject(ERROR_MESSAGE, e.getMessage());
        }
        modelAndView.setViewName(AVG_SALARY_JSP_URL);
        return modelAndView;
    }
}
