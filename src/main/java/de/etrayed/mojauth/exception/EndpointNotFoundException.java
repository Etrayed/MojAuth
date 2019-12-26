package de.etrayed.mojauth.exception;

/**
 * @author Etrayed
 */
public class EndpointNotFoundException extends ExceptionWithStatusCode {

    public EndpointNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}
