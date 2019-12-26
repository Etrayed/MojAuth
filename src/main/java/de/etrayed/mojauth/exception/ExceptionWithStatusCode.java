package de.etrayed.mojauth.exception;

/**
 * @author Etrayed
 */
public abstract class ExceptionWithStatusCode extends Throwable {

    private final int statusCode;

    public ExceptionWithStatusCode(String message, int statusCode) {
        super(message);

        this.statusCode = statusCode;
    }

    public ExceptionWithStatusCode(Throwable cause, int statusCode) {
        super(cause);

        this.statusCode = statusCode;
    }

    public int statusCode() {
        return statusCode;
    }
}