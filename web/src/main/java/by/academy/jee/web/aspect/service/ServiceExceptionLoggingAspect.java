package by.academy.jee.web.aspect.service;

import by.academy.jee.exception.DaoException;
import by.academy.jee.exception.ServiceException;
import by.academy.jee.util.DataBaseUtil;
import by.academy.jee.util.ThreadLocalForEntityManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceExceptionLoggingAspect {

    private final ThreadLocalForEntityManager emHelper = ThreadLocalForEntityManager.getInstance();

    @AfterThrowing(
            value = "@annotation(ServiceExceptionHandler)", throwing = "e"
    )
    @SneakyThrows
    public void loggingDaoExceptionsWithRethrowing(DaoException e) {
        DataBaseUtil.rollBack(emHelper.get());
        log.error("Exception {}", e.getMessage());
        throw new ServiceException(e.getMessage(), e);
    }
}
