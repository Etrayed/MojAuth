package de.etrayed.mojauth.result;

import de.etrayed.mojauth.util.Profile;
import de.etrayed.mojauth.util.UserInfo;

import java.util.Optional;

/**
 * @author Etrayed
 */
public class RefreshResult {

    private final String accessToken, clientToken;

    private final Profile selectedProfile;

    private final UserInfo userInfo;

    public RefreshResult(String accessToken, String clientToken, Profile selectedProfile, UserInfo userInfo) {
        this.accessToken = accessToken;
        this.clientToken = clientToken;
        this.selectedProfile = selectedProfile;
        this.userInfo = userInfo;
    }

    public String accessToken() {
        return accessToken;
    }

    public String clientToken() {
        return clientToken;
    }

    public Profile selectedProfile() {
        return selectedProfile;
    }

    public Optional<UserInfo> userInfo() {
        return Optional.of(userInfo);
    }
}
