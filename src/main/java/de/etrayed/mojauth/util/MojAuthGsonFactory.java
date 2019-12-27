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
public class MojAuthGsonFactory {

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
                String entryName = nextNameOrEnd(jsonReader);

                if(entryName == null) {
                    continue;
                }

                if (entryName.equals("id")) {
                    id = hexStringToUUID(jsonReader.nextString());
                } else if (entryName.equals("name")) {
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

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String entryName = nextNameOrEnd(jsonReader);

                if(entryName == null) {
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

    private static String nextNameOrEnd(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.END_OBJECT) {
            reader.endObject();

            return null;
        }

        String entryName = reader.nextName();

        if(reader.peek() == JsonToken.NULL) {
            reader.skipValue();

            return null;
        }

        return entryName;
    }

    private static UUID hexStringToUUID(String str) {
        return UUID.fromString(str.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5"));
    }
}
