package de.etrayed.mojauth.response;

import com.google.gson.JsonObject;
import de.etrayed.mojauth.result.RefreshResult;

/**
 * @author Etrayed
 */
public class RefreshResponse extends AbstractResponse<RefreshResult> {

    public RefreshResponse(JsonObject object, int statusCode) {
        super(object, statusCode);
    }

    @Override
    RefreshResult constructResult(JsonObject object, int statusCode) {
        return null;
    }
}
