package by.academy.jee.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseUtil {

    private DataBaseUtil() {
        //util class
    }

    private static final Logger log = LoggerFactory.getLogger(DataBaseUtil.class);

    public static void closeQuietly(AutoCloseable closeable) {

        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
            log.error("Couldn't close {}", closeable);
        }
    }
}
