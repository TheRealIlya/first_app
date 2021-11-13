package by.academy.jee.exception;

public class PasswordHashingException extends RuntimeException{

    public PasswordHashingException() {
    }

    public PasswordHashingException(String message) {
        super(message);
    }

    public PasswordHashingException(String message, Throwable cause) {
        super(message, cause);
    }
}
