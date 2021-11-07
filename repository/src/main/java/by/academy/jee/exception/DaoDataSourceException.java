package by.academy.jee.exception;

public class DaoDataSourceException extends RuntimeException {

    public DaoDataSourceException() {
    }

    public DaoDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
