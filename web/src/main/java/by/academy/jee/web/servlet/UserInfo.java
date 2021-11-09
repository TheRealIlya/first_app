package by.academy.jee.web.servlet;

import by.academy.jee.dao.DaoDataSource;
import by.academy.jee.dao.person.teacher.TeacherDaoForPostgres;
import by.academy.jee.model.person.Teacher;
import by.academy.jee.web.util.SessionUtil;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.sql.DataSource;
import static by.academy.jee.web.constant.Constant.USER_INFO_JSP_URL;

@WebServlet(value = "/userInfo")
public class UserInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        List<Teacher> list = TeacherDaoForPostgres.getInstance(DaoDataSource.getInstance("jdbc:postgresql://localhost:5432/academy",
                "ilya", "postgres", "org.postgresql.Driver")).readAll();
        for (Teacher teacher : list) {
            writer.write(teacher.toString());
        }
        //SessionUtil.setupForward(this, req, resp, USER_INFO_JSP_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
