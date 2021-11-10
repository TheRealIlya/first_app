package by.academy.jee.util;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static by.academy.jee.constant.Constant.COULDNT_CLOSE_SOME_CLOSEABLE_ELEMENT;
import static by.academy.jee.constant.Constant.FAILED_TO_ROLLBACK;

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
}