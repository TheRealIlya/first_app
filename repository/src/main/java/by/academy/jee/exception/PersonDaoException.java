package by.academy.jee.exception;

public class PersonDaoException extends RuntimeException {

    public PersonDaoException() {
    }

    public PersonDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
