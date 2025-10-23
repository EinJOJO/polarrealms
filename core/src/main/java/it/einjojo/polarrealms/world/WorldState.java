package it.einjojo.polarrealms.world;

/**
 * Describes the state of a realm
 */
public enum WorldState {
    /**
     * Default state
     */
    UNLOADED,
    /**
     * A host has been selected, and the world is being loaded.
     */
    LOADING,
    /**
     * The world is loaded on a host, but no players are connected to it.
     */
    IDLE,
    /**
     * The world is loaded on a host and at least one player is connected to it.
     */
    ACTIVE,
    /**
     * After a /period of inactivity, bukkit unloads the world but keeps the lock.
     * When a player joins the world, the host system will load the world.
     * This gives other realms on the same host more resources.
     */
    FROZEN,
    /**
     * The world is being unloaded.
     */
    UNLOADING;

    public static WorldState fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= WorldState.values().length) {
            throw new IllegalArgumentException("Invalid ordinal: " + ordinal);
        }
        return WorldState.values()[ordinal];
    }

}
