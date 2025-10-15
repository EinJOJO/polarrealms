package it.einjojo.polarrealms.repository;


import it.einjojo.polarrealms.world.RealmWorld;

import java.util.*;


/**
 * Interface representing the database storage operations for RealmWorlds.
 */
public interface RealmRepository {


    /**
     * Saves the specified realm world.
     *
     * @param realmWorld the realm world to save
     */
    void saveRealm(RealmWorld realmWorld);

    /**
     * Deletes the specified realm world.
     *
     * @param realmWorld the realm world to delete
     */
    void deleteRealm(RealmWorld realmWorld);

    /**
     * Loads a realm world by its ID.
     *
     * @param realmId the ID of the realm world to load
     * @return an Optional containing the loaded realm world, or empty if not found
     */
    Optional<RealmWorld> loadRealmById(UUID realmId);

    /**
     * Loads multiple realm worlds by their IDs.
     *
     * @param ids the collection of IDs of the realm worlds to load
     * @return a list of loaded realm worlds
     */
    List<RealmWorld> loadRealms(Collection<UUID> ids);

    /**
     * Loads all realm IDs.
     *
     * @return a list of all realm IDs
     */
    List<UUID> loadAllRealmIds();

    /**
     * Loads all realm IDs of a player.
     *
     * @param playerId player's UUID
     * @return a list of realm IDs owned by the player
     */
    List<UUID> loadRealmsIdsByOwner(UUID playerId);

    /**
     * Loads all realm aliases of a realm owner.
     *
     * @param realmOwner realm owner's UUID
     * @return a list of realm aliases owned by the player
     */
    List<String> loadRealmAliases(UUID realmOwner);

}