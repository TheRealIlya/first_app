package by.academy.jee.web.servlet.student;

import by.academy.jee.web.util.SessionUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static by.academy.jee.web.constant.Constant.CHECK_GRADES_JSP_URL;

@WebServlet(value = "/checkGrades")
public class CheckGradesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUtil.setupForward(this, req, resp, CHECK_GRADES_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
