package de.etrayed.mojauth.response;

import com.google.gson.JsonObject;

/**
 * @author Etrayed
 */
public class EmptyIfSuccessfulResponse extends AbstractResponse<Void> {

    public EmptyIfSuccessfulResponse(JsonObject object, int statusCode) {
        super(object, statusCode);
    }

    @Override
    Void constructResult(JsonObject object, int statusCode) {
        return null;
    }
}
