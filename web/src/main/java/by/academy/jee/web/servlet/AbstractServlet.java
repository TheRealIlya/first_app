package by.academy.jee.web.servlet;

import by.academy.jee.web.service.Service;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AbstractServlet extends HttpServlet {

    protected Service service;
    private ConfigurableApplicationContext ctx;
    private boolean isFirst = true;

    @Override
    public void init() throws ServletException {
        if (isFirst) {
            isFirst = false;
            ctx = new AnnotationConfigApplicationContext("by.academy.jee");
            service = ctx.getBean("service", Service.class);
        }
    }
}
