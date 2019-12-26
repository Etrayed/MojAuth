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
    AuthenticationResult constructResult(JsonObject object, int statusCode) {
        String accessToken = object.get("accessToken").getAsString();
        String clientToken = object.has("clientToken") ? object.get("clientToken").getAsString() : null;
        Profile selectedProfile = MojAuth.GSON.fromJson(object.get("selectedProfile").getAsJsonObject(), Profile.class);
        UserInfo userInfo = object.has("user") ? MojAuth.GSON.fromJson(object.get("user").getAsJsonObject(),
                UserInfo.class) : null;
        List<Profile> availableProfiles = object.has("availableProfiles") ? new CopyOnWriteArrayList<>()
                : null;

        if(availableProfiles != null) {
            for (JsonElement availableProfile : object.get("availableProfiles").getAsJsonArray()) {
                availableProfiles.add(MojAuth.GSON.fromJson(availableProfile.getAsJsonObject(), Profile.class));
            }
        }

        return new AuthenticationResult(accessToken, clientToken, selectedProfile, userInfo, availableProfiles);
    }
}
