package de.etrayed.mojauth.util;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Etrayed
 */
public class UserInfo extends Profile {

    private final Map<String, String> properties;

    public UserInfo(UUID id, String name, Map<String, String> properties) {
        super(id, name);

        this.properties = properties;
    }

    public String getProperty(String name) {
        return properties.get(name);
    }

    public List<String> propertyNames() {
        return new CopyOnWriteArrayList<>(properties.keySet());
    }
}
