package de.etrayed.mojauth.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.etrayed.mojauth.MojAuth;
import de.etrayed.mojauth.result.AuthenticationResult;
import de.etrayed.mojauth.util.Profile;
import de.etrayed.mojauth.util.UserInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Etrayed
 */
public class AuthenticationResponse extends AbstractResponse<AuthenticationResult> {

    public AuthenticationResponse(JsonObject object, int statusCode) {
        super(object, statusCode);
    }

    @Override
    AuthenticationResult constructResult(JsonObject object) {
        String accessToken = object.get("accessToken").getAsString();
        String clientToken = getOrDefault(object.get("clientToken"), JsonElement::getAsString);
        Profile selectedProfile = convertToJsonOrDefault(object.get("selectedProfile"), Profile.class);
        UserInfo userInfo = convertToJsonOrDefault(object.get("user"), UserInfo.class);
        List<Profile> availableProfiles;

        if (object.has("availableProfiles")) {
            availableProfiles = new CopyOnWriteArrayList<>();

            object.getAsJsonArray("availableProfiles").forEach(profile -> availableProfiles.add(MojAuth
                    .GSON.fromJson(profile.getAsJsonObject(), Profile.class)));
        } else {
            availableProfiles = null;
        }

        return new AuthenticationResult(accessToken, clientToken, selectedProfile, userInfo, availableProfiles);
    }
}
