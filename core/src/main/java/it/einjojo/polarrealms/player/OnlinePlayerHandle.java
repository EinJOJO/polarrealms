package it.einjojo.polarrealms.player;

import it.einjojo.polarrealms.world.Location;

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

    CompletableFuture<String> getServerName();

    /**
     *
     * Thread safe method to teleport the player to a location.
     *
     * @param location the location to teleport the player to.
     * @throws UnsupportedOperationException if the player cannot be teleported (e.g. on velocity servers)
     */
    void teleport(Location location) throws UnsupportedOperationException;

}
