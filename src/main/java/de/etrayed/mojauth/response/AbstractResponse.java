package de.etrayed.mojauth.response;

import com.google.gson.JsonObject;
import de.etrayed.mojauth.exception.*;

/**
 * @author Etrayed
 */
abstract class AbstractResponse<R> implements Response<R> {

    private final String plainBody;

    private final Throwable error;

    private final R result;

    public AbstractResponse(JsonObject object, int statusCode) {
        this.plainBody = object.toString();

        Throwable error = null;

        if(object.has("error")) {
            String message = object.get("errorMessage").getAsString();

            switch (object.get("error").getAsString()) {
                case "Method Not Allowed":
                    error = new MethodNotAllowedException(message, statusCode);

                    break;
                case "Not Found":
                    error = new EndpointNotFoundException(message, statusCode);

                    break;
                case "ForbiddenOperationException":
                    error = object.has("cause")
                            ? new ForbiddenOperationException(new UserMigratedException(message, statusCode), statusCode)
                            : new ForbiddenOperationException(message, statusCode);

                    break;
                case "IllegalArgumentException":
                    error = new IllegalArgumentException(message);

                    break;
                case "Unsupported Media Type":
                    error = new UnsupportedMediaTypeException(message
                            + " (set \"Content-Type\" header to \"application/json\")", statusCode);

                    break;

                default:
                    error = new MojangAuthException(message, statusCode);

                    break;
            }
        }

        this.error = error;
        this.result = error == null ? constructResult(object, statusCode) : null;
    }

    abstract R constructResult(JsonObject object, int statusCode);

    @Override
    public final String plainBody() {
        return plainBody;
    }

    @Override
    public final Throwable error() {
        return error;
    }

    @Override
    public final R result() {
        return result;
    }
}
