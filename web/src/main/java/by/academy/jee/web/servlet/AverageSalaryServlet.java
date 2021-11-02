package by.academy.jee.web.servlet;

import by.academy.jee.web.util.SessionUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static by.academy.jee.web.constant.Constant.AVG_SALARY_JSP_URL;

@WebServlet(value = "/avgSalary")
public class AverageSalaryServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(AverageSalaryServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, AVG_SALARY_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
