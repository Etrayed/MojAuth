package de.etrayed.mojauth.util;

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

    public Game game() {
        return game;
    }

    public int version() {
        return version;
    }

    public enum Game {

        MINECRAFT("Minecraft"),
        SCROLLS("Scrolls");

        private final String name;

        Game(String name) {
            this.name = name;
        }

        public String fixedName() {
            return name;
        }
    }
}
