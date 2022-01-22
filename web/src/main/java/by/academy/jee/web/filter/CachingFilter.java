package by.academy.jee.web.filter;

import by.academy.jee.web.controller.interceptor.RealCachingRequestWrapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;

public class CachingFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        RealCachingRequestWrapper reqWrapper = new RealCachingRequestWrapper((HttpServletRequest) req);
        reqWrapper.getParameterMap();
        chain.doFilter(reqWrapper, resp);
    }
}
