package de.etrayed.mojauth.exception;

/**
 * @author Etrayed
 */
public class ForbiddenOperationException extends ExceptionWithStatusCode {

    public ForbiddenOperationException(String message, int statusCode) {
        super(message, statusCode);
    }

    public ForbiddenOperationException(Throwable cause, int statusCode) {
        super(cause, statusCode);
    }
}
