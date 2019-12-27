package de.etrayed.mojauth.response;

import java.util.Optional;

/**
 * @author Etrayed
 */
public interface Response<T> {

    Optional<Throwable> error();

    String plainBody();

    Optional<T> result();
}
