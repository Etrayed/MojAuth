package de.etrayed.mojauth.util;

import com.google.gson.JsonObject;

/**
 * @author Etrayed
 */
public class AuthAgent {

    private final Game game;

    private final int version;

    public AuthAgent(Game game, int version) {
        this.game = game;
        this.version = version;
    }

    public JsonObject jsonify() {
        JsonObject object = new JsonObject();

        object.addProperty("name", game.name);
        object.addProperty("version", version);

        return object;
    }

    public enum Game {

        MINECRAFT("Minecraft"),
        SCROLLS("Scrolls");

        private final String name;

        Game(String name) {
            this.name = name;
        }
    }
}
