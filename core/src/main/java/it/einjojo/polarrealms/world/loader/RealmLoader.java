package it.einjojo.polarrealms.world.loader;

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

    CompletableFuture<RealmWorld> createRealm();

    RealmStateManager getStateManager();

}
