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
import static by.academy.jee.constant.CommonConstant.REPOSITORY_PROPERTIES;

@Aspect
@Component
@Slf4j
@PropertySource(REPOSITORY_PROPERTIES)
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

        if (RepositoryType.JPA.equals(TYPE)) {
            emHelper.set();
            emHelper.get().getTransaction().begin();
        }
    }

    private void closeTransaction() {

        if (RepositoryType.JPA.equals(TYPE)) {
            DataBaseUtil.closeEntityManager(emHelper.get());
            DataBaseUtil.finallyCloseEntityManager(emHelper.get());
            emHelper.remove();
        }
    }
}
