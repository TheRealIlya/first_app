package by.academy.jee.web.filter;

import by.academy.jee.model.person.Person;
import by.academy.jee.web.util.SessionUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Person user = SessionUtil.getSessionUser(req);
        if (user == null) {
            RequestDispatcher dispatcher = req.getServletContext().
                    getRequestDispatcher("/");
            dispatcher.forward(req, resp);
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
