package de.etrayed.mojauth.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.etrayed.mojauth.MojAuth;
import de.etrayed.mojauth.util.MojAuthException;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Etrayed
 */
abstract class AbstractResponse<R> implements Response<R> {

    private final String plainBody;

    private final Throwable error;

    private final R result;

    public AbstractResponse(JsonObject object, int statusCode) {
        this.plainBody = object.toString();

        MojAuthException.Type type = MojAuthException.parseType(object.get("error"));

        if(type == null) {
            this.error = null;
            this.result = constructResult(object);
        } else {
            this.error = new MojAuthException(type, object.get("errorMessage").getAsString(), statusCode);
            this.result = null;
        }
    }

    abstract R constructResult(JsonObject object);

    <T> T getOrDefault(JsonElement element, Function<JsonElement, T> converter) {
        if(element == null || element.isJsonNull()) {
            return null;
        }

        return converter.apply(element);
    }

    <T> T convertToJsonOrDefault(JsonElement element, Class<T> outClass) {
        if(element == null || element.isJsonNull()) {
            return null;
        }

        return MojAuth.GSON.fromJson(element, outClass);
    }

    @Override
    public final String plainBody() {
        return plainBody;
    }

    @Override
    public final Optional<Throwable> error() {
        return Optional.of(error);
    }

    @Override
    public final Optional<R> result() {
        return Optional.of(result);
    }
}
