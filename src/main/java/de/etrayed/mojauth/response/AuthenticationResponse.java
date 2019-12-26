package de.etrayed.mojauth.response;

import com.google.gson.JsonObject;
import de.etrayed.mojauth.result.AuthenticationResult;

/**
 * @author Etrayed
 */
public class AuthenticationResponse extends AbstractResponse<AuthenticationResult> {

    public AuthenticationResponse(JsonObject object, int statusCode) {
        super(object, statusCode);
    }

    @Override
    AuthenticationResult constructResult(JsonObject object, int statusCode) {
        return null;
    }
}
