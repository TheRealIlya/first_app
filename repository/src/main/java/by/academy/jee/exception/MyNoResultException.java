package by.academy.jee.exception;

public class MyNoResultException extends RuntimeException {

    public MyNoResultException(String message) {
        super(message);
    }

    public MyNoResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
