package de.etrayed.mojauth.util;

import java.util.UUID;

/**
 * @author Etrayed
 */
public class Profile {

    private final UUID id;

    private final String name;

    @SuppressWarnings("WeakerAccess")
    public Profile(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }
}
