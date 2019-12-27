package de.etrayed.mojauth.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Etrayed
 */
public class MojAuthException extends Throwable {

    private static final Map<String, Type> DESC_TO_TYPE;

    static {
        DESC_TO_TYPE = new ConcurrentHashMap<>();

        DESC_TO_TYPE.put("Method Not Allowed", Type.METHOD_NOT_ALLOWED);
        DESC_TO_TYPE.put("Not Found", Type.ENDPOINT_NOT_FOUND);
        DESC_TO_TYPE.put("ForbiddenOperationException", Type.FORBIDDEN_OPERATION);
        DESC_TO_TYPE.put("Unsupported Media Type", Type.UNSUPPORTED_MEDIA_TYPE);
    }

    private final Type type;

    private final int statusCode;

    public MojAuthException(Type type, String message, int statusCode) {
        super(message);

        this.type = type;
        this.statusCode = statusCode;
    }

    public Type type() {
        return type;
    }

    public int statusCode() {
        return statusCode;
    }

    public static Type parseType(JsonElement element) {
        if(element == null || !(element instanceof JsonPrimitive)) {
            return null;
        }

        return DESC_TO_TYPE.getOrDefault(element.getAsString(), Type.OTHER);
    }

    public enum Type {

        METHOD_NOT_ALLOWED,
        UNSUPPORTED_MEDIA_TYPE,
        ENDPOINT_NOT_FOUND,
        FORBIDDEN_OPERATION,
        OTHER
    }
}
