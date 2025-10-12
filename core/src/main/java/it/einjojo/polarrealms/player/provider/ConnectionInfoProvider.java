package it.einjojo.polarrealms.player.provider;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides information about a player's connection status.
 * <p>This information is used to determine realm access rules and displaying connection information.</p>
 */
public interface ConnectionInfoProvider {

    /**
     * Checks if the player is connected to the network.
     *
     * @return true if the player is connected to the network and false otherwise
     */
    boolean isOnline(UUID playerId);

    /**
     * Get Server Name of player
     *
     * @return server name if connected to a server, empty otherwise
     */
    Optional<String> getServerName(UUID playerId);

}
