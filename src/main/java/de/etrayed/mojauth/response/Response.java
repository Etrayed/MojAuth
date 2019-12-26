package de.etrayed.mojauth.response;

/**
 * @author Etrayed
 */
public interface Response<T> {

    Throwable error();

    String plainBody();

    T result();
}
