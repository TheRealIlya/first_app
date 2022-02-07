package by.academy.jee.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import static by.academy.jee.constant.ExceptionConstant.COULDNT_CLOSE_SOME_CLOSEABLE_ELEMENT;
import static by.academy.jee.constant.ExceptionConstant.FAILED_TO_ROLLBACK;

@Slf4j
public class DataBaseUtil {

    private DataBaseUtil() {
        //util class
    }

    public static void closeQuietly(AutoCloseable... closeable) {

        if (closeable == null) {
            return;
        }
        try {
            for (AutoCloseable autoCloseable : closeable) {
                autoCloseable.close();
            }
        } catch (Exception e) {
            log.error(COULDNT_CLOSE_SOME_CLOSEABLE_ELEMENT, e);
        }
    }

    public static void rollBack(Connection con) {

        if (con == null) {
            return;
        }
        try {
            con.rollback();
        } catch (SQLException e) {
            log.error(FAILED_TO_ROLLBACK, e);
        }
    }

    public static void rollBack(EntityManager em) {

        if (em != null) {
            em.getTransaction().rollback();
        }
    }

    public static void finallyCloseEntityManager(EntityManager em) {

        if (em != null) {
            try {
                em.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public static void closeEntityManager(EntityManager em) {

        if (em != null) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
    }
}
