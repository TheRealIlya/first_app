package by.academy.jee.web.filter;

import by.academy.jee.model.person.Person;
import by.academy.jee.web.util.SessionUtil;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter("/jsp/*")
//public class LoginFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;
//        Person user = SessionUtil.getSessionUser(req);
//        if (user == null) {
//            RequestDispatcher dispatcher = req.getServletContext().
//                    getRequestDispatcher("/");
//            dispatcher.forward(req, resp);
//        } else {
//            filterChain.doFilter(req, resp);
//        }
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
