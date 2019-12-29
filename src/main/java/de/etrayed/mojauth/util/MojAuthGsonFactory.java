package de.etrayed.mojauth.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Etrayed
 */
public class MojAuthGsonFactory {

    public static Gson newInstance() {
        return new GsonBuilder().setPrettyPrinting().serializeNulls()
                .registerTypeAdapter(Profile.class, new ProfileDeserializer())
                .registerTypeAdapter(UserInfo.class, new UserInfoDeserializer()).create();
    }

    private static final class ProfileDeserializer implements JsonDeserializer<Profile> {

        @Override
        public Profile deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            if(!jsonElement.isJsonObject()) {
                throw new JsonParseException("not a JsonObject");
            }

            JsonObject profileObject = jsonElement.getAsJsonObject();

            return new Profile(hexStringToUUID(profileObject.get("id").getAsString()),
                    profileObject.get("name").getAsString());
        }
    }

    private static final class UserInfoDeserializer implements JsonDeserializer<UserInfo> {

        @Override
        public UserInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            if(!jsonElement.isJsonObject()) {
                throw new JsonParseException("not a JsonObject");
            }

            JsonObject userInfoObject = jsonElement.getAsJsonObject();

            return new UserInfo(hexStringToUUID(userInfoObject.get("id").getAsString()),
                    userInfoObject.get("username").getAsString(),
                    jsonArrayToPropertyMap(userInfoObject.getAsJsonArray("properties")));
        }
    }

    private static Map<String, String> jsonArrayToPropertyMap(JsonArray array) {
        Map<String, String> propertyMap = new ConcurrentHashMap<>();

        array.forEach(element -> {
            JsonObject object = element.getAsJsonObject();

            propertyMap.put(object.get("name").getAsString(), object.get("value").getAsString());
        });

        return propertyMap;
    }

    private static UUID hexStringToUUID(String str) {
        return UUID.fromString(str.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5"));
    }
}
