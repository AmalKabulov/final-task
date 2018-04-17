package by.epam.processor.exception;

public class MappingIncorrectException extends RuntimeException {
    public MappingIncorrectException() {
        super();
    }

    public MappingIncorrectException(String message) {
        super(message);
    }

    public MappingIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingIncorrectException(Throwable cause) {
        super(cause);
    }

    protected MappingIncorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
