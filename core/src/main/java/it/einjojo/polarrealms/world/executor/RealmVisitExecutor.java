package it.einjojo.polarrealms.world.executor;

import it.einjojo.polarrealms.player.OnlinePlayerHandle;
import it.einjojo.polarrealms.world.RealmWorld;

import java.util.concurrent.CompletableFuture;

/**
 * Visiting a realm is quite complex.
 * Depending on the server environment, the logic might be different. I'd like to keep the possibility to extend it.
 * <ul>
 *     <li>Connect the player to servers host? (Not required on single server environments)</li>
 *     <li>Preload the realm if it is not loaded. Trigger state changes. Concurrency management.</li>
 *     <li>Teleport the player to the correct world</li>
 *     <li>Rate limiting? </li>
 * </ul>
 *
 */
public interface RealmVisitExecutor {

    CompletableFuture<Void> visit(RealmWorld realmWorld, OnlinePlayerHandle visitor);
    
}
