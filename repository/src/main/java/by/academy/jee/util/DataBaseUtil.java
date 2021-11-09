package by.academy.jee.util;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseUtil {

    private DataBaseUtil() {
        //util class
    }

    private static final Logger log = LoggerFactory.getLogger(DataBaseUtil.class);

    public static void closeQuietly(AutoCloseable... closeable) {

        if (closeable == null) {
            return;
        }
        try {
            for (AutoCloseable autoCloseable : closeable) {
                autoCloseable.close();
            }

        } catch (Exception e) {
            log.error("Couldn't close {}", closeable);
        }
    }

    public static void rollBack(Connection con) {
        if (con == null) {
            return;
        }
        try {
            con.rollback();
        } catch (SQLException e) {
            log.error("Failed to rollback", e);
        }
    }
}
