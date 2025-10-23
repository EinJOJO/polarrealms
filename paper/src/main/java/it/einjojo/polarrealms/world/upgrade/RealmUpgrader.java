package it.einjojo.polarrealms.world.upgrade;

import it.einjojo.polarrealms.world.RealmHandle;

import java.util.concurrent.CompletableFuture;

/**
 * Upgrades a realm world.
 */
public interface RealmUpgrader {

    CompletableFuture<Void> upgradeRealm(RealmHandle realmHandle);

}
