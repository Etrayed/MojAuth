package de.etrayed.mojauth.exception;

/**
 * @author Etrayed
 */
public class UserMigratedException extends ExceptionWithStatusCode {

    public UserMigratedException(String message, int statusCode) {
        super(message, statusCode);
    }
}
