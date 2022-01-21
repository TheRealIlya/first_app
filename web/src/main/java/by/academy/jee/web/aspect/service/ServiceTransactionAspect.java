package by.academy.jee.web.aspect.service;

import by.academy.jee.dao.RepositoryType;
import by.academy.jee.util.DataBaseUtil;
import by.academy.jee.util.ThreadLocalForEntityManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@PropertySource("classpath:repository.properties")
public class ServiceTransactionAspect {

    private final ThreadLocalForEntityManager emHelper = ThreadLocalForEntityManager.getInstance();
    private final RepositoryType TYPE;

    public ServiceTransactionAspect(@Value("${repository.type}") String type) {
        TYPE = RepositoryType.getTypeByString(type.toLowerCase());
    }

    @SneakyThrows
    @Around("@annotation(ServiceTransaction)")
    public Object transaction(ProceedingJoinPoint jp) {

        beginTransaction();
        String method = jp.getSignature().getName();
        log.info("Transaction begins, method {}", method);
        try {
            return jp.proceed();
        } finally {
            closeTransaction();
            log.info("Transaction ends, method {}", method);
        }
    }

    private void beginTransaction() {

        switch (TYPE) {
            case MEMORY:

            case POSTGRES:

            case JPA:
            default:
                emHelper.set();
                emHelper.get().getTransaction().begin();
        }
    }

    private void closeTransaction() {

        switch (TYPE) {
            case MEMORY:

            case POSTGRES:

            case JPA:
            default:
                DataBaseUtil.closeEntityManager(emHelper.get());
                DataBaseUtil.finallyCloseEntityManager(emHelper.get());
                emHelper.remove();
        }
    }
}
