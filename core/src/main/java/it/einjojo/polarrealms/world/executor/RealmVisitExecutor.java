package it.einjojo.polarrealms.world.executor;

import it.einjojo.polarrealms.world.loader.RealmLoader;

/**
 * Visiting a realm is quite complex.
 * Depending on the server environment, the logic might be different. I'd like to keep the possibility to extend it.
 * <ul>
 *     <li>Connect the player to servers host? (Not required on single server environments)</li>
 *     <li>Preload the realm if it is not loaded. Trigger state changes. Concurrency management.</li>
 *     <li>Teleport the player to the correct world</li>
 * </ul>
 *
 */
public interface RealmVisitExecutor {

    RealmLoader getRealmLoader();
}
