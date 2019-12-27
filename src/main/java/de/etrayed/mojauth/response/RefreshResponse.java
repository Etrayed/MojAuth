package de.etrayed.mojauth.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
    RefreshResult constructResult(JsonObject object) {
        String accessToken = object.get("accessToken").getAsString();
        String clientToken = getOrDefault(object.get("clientToken"), JsonElement::getAsString);
        Profile selectedProfile = convertToJsonOrDefault(object.get("selectedProfile"), Profile.class);
        UserInfo userInfo = convertToJsonOrDefault(object.get("user"), UserInfo.class);

        return new RefreshResult(accessToken, clientToken, selectedProfile, userInfo);
    }
}
