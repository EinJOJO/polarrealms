package it.einjojo.polarrealms.player;


import java.util.UUID;

/**
 * Creates a new {@link OnlinePlayerHandle} instance.
 */
public interface OnlinePlayerHandleFactory {

    /**
     * Get {@link OnlinePlayerHandle} for a player UUID.
     * Might perform more checks?
     *
     * @param uuid player's UUID
     * @return a {@link OnlinePlayerHandle} instance
     * @throws IllegalStateException if the player is not online
     */
    OnlinePlayerHandle createHandle(UUID uuid) throws IllegalStateException;

    /**
     * Get {@link OnlinePlayerHandle} for a platform player object. (preferred method)
     *
     * @param platformPlayerObject on Paper it is "org.bukkit.entity.Player"
     * @return a {@link OnlinePlayerHandle} instance
     * @throws IllegalStateException if the player is not online or the platform player object is not supported
     */
    OnlinePlayerHandle createHandle(Object platformPlayerObject) throws IllegalStateException;
}
