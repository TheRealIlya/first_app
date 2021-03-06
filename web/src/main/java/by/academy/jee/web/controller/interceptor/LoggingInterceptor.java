package by.academy.jee.web.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        logUrl(req);
        logBody(req);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView modelAndView) throws Exception {
        ContentCachingResponseWrapper respWrapper = (ContentCachingResponseWrapper) resp;
        String body = new String(respWrapper.getContentAsByteArray(), req.getCharacterEncoding());
        log.debug("Response body:\n" + body);
    }

    private void logUrl(HttpServletRequest req) {
        log.info("{} {}", req.getMethod(), ServletUriComponentsBuilder.fromRequest(req).toUriString());
    }

    @SneakyThrows
    private void logBody(HttpServletRequest req) {
        RealCachingRequestWrapper reqWrapper = (RealCachingRequestWrapper) req;
        String body = new String(reqWrapper.getContentAsByteArray(), req.getCharacterEncoding());
        log.debug("Request body:\n" + body);
    }
}
