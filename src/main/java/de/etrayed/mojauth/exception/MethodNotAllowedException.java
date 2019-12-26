package de.etrayed.mojauth.exception;

/**
 * @author Etrayed
 */
public class MethodNotAllowedException extends ExceptionWithStatusCode {

    public MethodNotAllowedException(String message, int statusCode) {
        super(message, statusCode);
    }
}
