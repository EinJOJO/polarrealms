package it.einjojo.polarrealms.world.loader;

import it.einjojo.polarrealms.world.ActiveRealmSnapshot;
import it.einjojo.polarrealms.world.CreationContext;
import it.einjojo.polarrealms.world.RealmWorld;

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
     * Loads the realm asynchronously or returns an existing loaded realm.
     *
     * @return a CompletableFuture containing the loaded realm world
     */
    CompletableFuture<ActiveRealmSnapshot> withLoadedRealm();
}
