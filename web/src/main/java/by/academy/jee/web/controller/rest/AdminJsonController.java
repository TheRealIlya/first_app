package by.academy.jee.web.controller.rest;

import by.academy.jee.exception.ServiceException;
import by.academy.jee.model.person.Admin;
import by.academy.jee.web.service.Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/rest/admins")
public class AdminJsonController {

    private final Service service;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Admin> getAllAdmins() {
        return service.getAllAdmins();
    }

    @GetMapping(value = "/{login}", produces = "application/json")
    @ResponseBody
    public Admin getAdmin(@PathVariable String login) {
        Admin admin;
        try {
            admin =  (Admin) service.getUserIfExist(login);
        } catch (ServiceException e) {
            admin = null;
        }
        return admin;
    }

    @PostMapping(produces = "application/json")
    @ResponseBody
    public Admin createAdmin(@RequestBody Admin admin) {
        return service.createAdmin(admin);
    }

    @PutMapping(produces = "application/json")
    @ResponseBody
    public Admin updateAdmin(@RequestBody Admin admin) {
        return service.updateAdmin(admin);
    }

    @DeleteMapping(value = "/{login}", produces = "application/json")
    @ResponseBody
    public Admin deleteAdmin(@PathVariable String login) {
        Admin admin;
        try {
            admin =  (Admin) service.getUserIfExist(login);
        } catch (ServiceException e) {
            admin = null;
        }
        service.removeAdminByLogin(login);
        return admin;
    }
}
