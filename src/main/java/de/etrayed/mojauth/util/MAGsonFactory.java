package de.etrayed.mojauth.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Etrayed
 */
public class MAGsonFactory {

    private static final ProfileTypeAdapter PROFILE_TYPE_ADAPTER;

    private static final UserInfoTypeAdapter USER_INFO_TYPE_ADAPTER;

    static {
        PROFILE_TYPE_ADAPTER = new ProfileTypeAdapter();
        USER_INFO_TYPE_ADAPTER = new UserInfoTypeAdapter();
    }

    public static Gson newInstance() {
        return new GsonBuilder().setPrettyPrinting().serializeNulls()
                .registerTypeAdapter(Profile.class, PROFILE_TYPE_ADAPTER)
                .registerTypeAdapter(UserInfo.class, USER_INFO_TYPE_ADAPTER).create();
    }

    private static final class ProfileTypeAdapter extends TypeAdapter<Profile> {

        @Override
        public void write(JsonWriter jsonWriter, Profile profile) {}

        @Override
        public Profile read(JsonReader jsonReader) throws IOException {
            UUID id = null;
            String name = null;

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                if(jsonReader.peek() == JsonToken.END_OBJECT) {
                    jsonReader.endObject();

                    break;
                }

                String entryName = jsonReader.nextName();

                if(jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();

                    continue;
                }

                if(entryName.equals("id")) {
                    id = hexStringToUUID(jsonReader.nextString());
                } else if(entryName.equals("name")) {
                    name = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }

            return new Profile(id, name);
        }
    }

    private static final class UserInfoTypeAdapter extends TypeAdapter<UserInfo> {

        @Override
        public void write(JsonWriter jsonWriter, UserInfo userInfo) {}

        @Override
        public UserInfo read(JsonReader jsonReader) throws IOException {
            UUID id = null;
            String username = null;
            Map<String, String> properties = new ConcurrentHashMap<>();

            while (jsonReader.hasNext()) {
                if(jsonReader.peek() == JsonToken.END_OBJECT) {
                    jsonReader.endObject();

                    break;
                }

                String entryName = jsonReader.nextName();

                if(jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();

                    continue;
                }

                switch (entryName) {
                    case "id":
                        id = hexStringToUUID(jsonReader.nextString());
                        break;
                    case "username":
                        username = jsonReader.nextString();
                        break;
                    case "properties":
                        jsonReader.beginArray();

                        while (jsonReader.peek() != JsonToken.END_ARRAY) {
                            jsonReader.beginObject();

                            String arrayEntryName = jsonReader.nextName();

                            if (arrayEntryName.equals("name")) {
                                String propertyName = jsonReader.nextString();

                                jsonReader.nextName();

                                properties.put(propertyName, jsonReader.nextString());
                            } else {
                                String propertyValue = jsonReader.nextString();

                                jsonReader.nextName();

                                properties.put(jsonReader.nextString(), propertyValue);
                            }

                            jsonReader.endObject();
                        }

                        jsonReader.endArray();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }

            return new UserInfo(id, username, properties);
        }
    }

    private static UUID hexStringToUUID(String str) {
        return UUID.fromString(str.substring(0, 8) + "-" + str.substring(8, 12) + "-" + str.substring(12, 16) + "-"
                + str.substring(16, 20) + "-" + str.substring(20, 32));
    }
}
