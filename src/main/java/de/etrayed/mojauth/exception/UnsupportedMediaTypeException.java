package de.etrayed.mojauth.exception;

/**
 * @author Etrayed
 */
public class UnsupportedMediaTypeException extends ExceptionWithStatusCode {

    public UnsupportedMediaTypeException(String message, int statusCode) {
        super(message, statusCode);
    }
}
