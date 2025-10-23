package it.einjojo.polarrealms.world.loader;

import it.einjojo.polarrealms.world.ActiveRealmSnapshot;
import it.einjojo.polarrealms.world.CreationContext;
import it.einjojo.polarrealms.world.RealmStateManager;
import it.einjojo.polarrealms.world.RealmWorld;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

/**
 * The loader is responsible for creating, loading, unloading a realm world.
 * <p>Because of this system architecture, there are two implementations planned:</p>
 * <ul>
 *     <li>Remote Loading Management (Redis based?)</li>
 *     <li>Host Systems Loader? (Paper Impl.) </li>
 * </ul>
 */
public interface RealmLoader {

    /**
     * Creates a new realm world.
     *
     * @return a CompletableFuture containing the created realm world
     */
    CompletableFuture<RealmWorld> createRealm(CreationContext context);

    RealmStateManager getStateManager();

    /**
     * Get an active realm and load the realm, if not loaded
     * <p>Preferred usage when an action is dedicated to a loaded realm, like visiting, because the future will always return a loaded realm if possible</p>
     *
     * @param realmWorld the realm world to load
     * @return a CompletableFuture containing the loaded realm world
     */
    CompletableFuture<ActiveRealmSnapshot> withLoadedRealm(@NonNull RealmWorld realmWorld);
}
