package it.einjojo.polarrealms.player.provider;

import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * Most of the time every server has a different way of providing usernames based given an uuid.
 * This is used to render realm owner name.
 */
public interface NameProvider {

    /**
     * Returns the username of a player.
     *
     * @param playerId the player's UUID
     * @return the username of the player
     */
    String getUsername(UUID playerId);

    /**
     * Get UUID by player name
     *
     * @param username the player's username
     * @return UUID of the player
     */
    UUID getUniqueId(String username);

    /**
     * Returns the display name of a player.
     *
     * @param playerId the player's UUID
     * @return the display name of the player
     */
    Component getDisplayName(UUID playerId);


}
