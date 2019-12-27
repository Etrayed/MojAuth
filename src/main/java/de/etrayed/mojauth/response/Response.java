package de.etrayed.mojauth.response;

import de.etrayed.mojauth.util.MojAuthException;

import java.util.Optional;

/**
 * @author Etrayed
 */
public interface Response<T> {

    Optional<MojAuthException> error();

    String plainBody();

    Optional<T> result();
}
