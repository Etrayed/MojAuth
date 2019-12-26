package de.etrayed.mojauth.response;

import com.google.gson.JsonObject;
import de.etrayed.mojauth.MojAuth;
import de.etrayed.mojauth.result.RefreshResult;
import de.etrayed.mojauth.util.Profile;
import de.etrayed.mojauth.util.UserInfo;

/**
 * @author Etrayed
 */
public class RefreshResponse extends AbstractResponse<RefreshResult> {

    public RefreshResponse(JsonObject object, int statusCode) {
        super(object, statusCode);
    }

    @Override
    RefreshResult constructResult(JsonObject object, int statusCode) {
        String accessToken = object.get("accessToken").getAsString();
        String clientToken = object.has("clientToken") ? object.get("clientToken").getAsString() : null;
        Profile selectedProfile = MojAuth.GSON.fromJson(object.get("selectedProfile").getAsJsonObject(), Profile.class);
        UserInfo userInfo = object.has("user") ? MojAuth.GSON.fromJson(object.get("user").getAsJsonObject(),
                UserInfo.class) : null;

        return new RefreshResult(accessToken, clientToken, selectedProfile, userInfo);
    }
}
