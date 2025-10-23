package it.einjojo.polarrealms.player;

import it.einjojo.polarrealms.world.Location;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface OnlinePlayerHandle {

    UUID getUniqueId();

    /**
     * Connects the player to a velocity server.
     *
     * @param velocityServerName the name of the velocity server to connect to.
     * @return a {@link CompletableFuture} that completes when the player is connected or fails exceptionally if the player could not be connected.
     */
    CompletableFuture<Void> connect(String velocityServerName);

    /**
     * Connects the player to a velocity server. Does not wait for the connection to complete.
     *
     * @param velocityServerName the name of the velocity server to connect to.
     */
    void connectSilently(String velocityServerName);

    /**
     * Get Server Name of player
     *
     * @return velocity server name if connected to a server, empty otherwise
     */
    CompletableFuture<@Nullable String> getServerName();

    /**
     * Thread safe method to teleport the player to a location.
     *
     * @param location the location to teleport the player to.
     * @throws UnsupportedOperationException if the player cannot be teleported (e.g. on velocity servers)
     * @throws java.util.NoSuchElementException if the world (realmId) is not found on this instance.
     */
    void teleport(Location location) throws UnsupportedOperationException;

}
