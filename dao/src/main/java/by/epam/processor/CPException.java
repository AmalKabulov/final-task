package by.epam.processor;

public class CPException extends Exception {
    public CPException() {
        super();
    }

    public CPException(String message) {
        super(message);
    }

    public CPException(String message, Throwable cause) {
        super(message, cause);
    }

    public CPException(Throwable cause) {
        super(cause);
    }

    protected CPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
