package by.academy.jee.web.aspect.dao;

import by.academy.jee.exception.DaoException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DaoExceptionLoggingAspect {

    @AfterThrowing(
            pointcut = "within(by.academy.jee.dao..*)",
            throwing = "e"
    )
    public void loggingExceptionWithRethrowing(Exception e) {
        log.error("Exception {}", e.getMessage());
        throw new DaoException(e.getMessage(), e);
    }
}
