package de.etrayed.mojauth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.etrayed.mojauth.response.AuthenticationResponse;
import de.etrayed.mojauth.response.EmptyIfSuccessfulResponse;
import de.etrayed.mojauth.response.RefreshResponse;
import de.etrayed.mojauth.util.AuthAgent;
import kong.unirest.Config;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;

import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;

/**
 * @author Etrayed
 */
public class MojAuth {

    private static final String AUTH_SERVER_URL = "https://authserver.mojang.com/";

    private static UnirestInstance unirestInstance;

    static {
        unirestInstance = new UnirestInstance(new Config());
    }

    public static AuthenticationResponse authenticate(AuthAgent agent, String username, String password,
                                                      boolean requestUser) {
        return authenticate(agent, username, password, null, requestUser);
    }

    public static AuthenticationResponse authenticate(AuthAgent agent, String username, String password,
                                                      String clientToken, boolean requestUser) {
        JsonObject object = new JsonObject();

        object.add("agent", agent.jsonify());
        object.addProperty("username", username);
        object.addProperty("password", password);
        object.addProperty("requestUser", requestUser);

        if(clientToken != null) {
            object.addProperty("clientToken", clientToken);
        }

        return checkForErrors(makeRequest("authenticate", object.toString()), AuthenticationResponse::new);
    }

    public static RefreshResponse refresh(String accessToken, boolean requestUser) {
        return refresh(accessToken, null, requestUser);
    }

    public static RefreshResponse refresh(String accessToken, String clientToken, boolean requestUser) {
        JsonObject object = new JsonObject();

        object.addProperty("accessToken", accessToken);
        object.addProperty("requestUser", requestUser);

        if(clientToken != null) {
            object.addProperty("clientToken", clientToken);
        }

        return checkForErrors(makeRequest("refresh", object.toString()), RefreshResponse::new);
    }

    public static boolean validate(String accessToken) {
        return validate(accessToken, null);
    }

    public static boolean validate(String accessToken, String clientToken) {
        JsonObject object = new JsonObject();

        object.addProperty("accessToken", accessToken);

        if(clientToken != null) {
            object.addProperty("clientToken", clientToken);
        }

        return makeRequest("validate", object.toString()).isSuccess();
    }

    public static EmptyIfSuccessfulResponse signOut(String username, String password) {
        JsonObject object = new JsonObject();

        object.addProperty("username", username);
        object.addProperty("password", password);

        return checkForErrors(makeRequest("signout", object.toString()), EmptyIfSuccessfulResponse::new);
    }

    public static EmptyIfSuccessfulResponse invalidate(String accessToken) {
        return invalidate(accessToken, null);
    }

    public static EmptyIfSuccessfulResponse invalidate(String accessToken, String clientToken) {
        JsonObject object = new JsonObject();

        object.addProperty("accessToken", accessToken);

        if(clientToken != null) {
            object.addProperty("clientToken", clientToken);
        }

        return checkForErrors(makeRequest("invalidate", object.toString()), EmptyIfSuccessfulResponse::new);
    }

    private static HttpResponse<String> makeRequest(String endpoint, String body) {
        return unirestInstance.post(AUTH_SERVER_URL + body).charset(StandardCharsets.UTF_8)
                .body(body).header("Content-Type", "application/json").asString();
    }

    private static <R> R checkForErrors(HttpResponse<String> response, BiFunction<JsonObject, Integer, R> function) {
        R resultResponse;

        if(response.isSuccess()) {
            resultResponse = function.apply(JsonParser.parseString(response.getBody()).getAsJsonObject(),
                    response.getStatus());
        } else if(response.getParsingError().isPresent()) {
            JsonObject errorObject = new JsonObject();

            errorObject.addProperty("error", ".");
            errorObject.addProperty("errorMessage", response.getParsingError().get().getMessage());

            resultResponse = function.apply(errorObject, response.getStatus());
        } else {
            resultResponse = function.apply(JsonParser.parseString(response.getBody()).getAsJsonObject(),
                    response.getStatus());
        }

        return resultResponse;
    }

    public static Config unirestConfig() {
        return unirestInstance.config();
    }

    public static void shutdown() {
        unirestInstance.shutDown(false);
    }
}
