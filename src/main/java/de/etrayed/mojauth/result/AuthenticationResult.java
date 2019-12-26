package de.etrayed.mojauth.result;

import de.etrayed.mojauth.util.Profile;
import de.etrayed.mojauth.util.UserInfo;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Etrayed
 */
public class AuthenticationResult extends RefreshResult {

    private final List<Profile> availableProfiles;

    public AuthenticationResult(String accessToken, String clientToken, Profile selectedProfile, UserInfo userInfo,
                                List<Profile> availableProfiles) {
        super(accessToken, clientToken, selectedProfile, userInfo);

        this.availableProfiles = availableProfiles;
    }

    public Optional<List<Profile>> availableProfiles() {
        return Optional.of(new CopyOnWriteArrayList<>(availableProfiles));
    }
}
