package de.etrayed.mojauth.exception;

/**
 * @author Etrayed
 */
public class MojangAuthException extends ExceptionWithStatusCode {

    public MojangAuthException(String message, int statusCode) {
        super(message, statusCode);
    }
}
