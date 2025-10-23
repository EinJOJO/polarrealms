package it.einjojo.polarrealms.world.executor;


import io.lettuce.core.api.StatefulRedisConnection;
import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.player.OnlinePlayerHandle;
import it.einjojo.polarrealms.player.PlayerConnectToRealmCallback;
import it.einjojo.polarrealms.world.ActiveRealmSnapshot;
import it.einjojo.polarrealms.world.RealmWorld;
import it.einjojo.polarrealms.world.loader.RealmLoader;
import it.einjojo.polarrealms.world.RealmStateManager;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


/**
 * Default implementation of {@link RealmVisitExecutor}.
 * //TODO write docs
 *
 */
@Getter
@NullMarked
public class DefaultRealmVisitExecutor implements RealmVisitExecutor {
    private final PolarRealms api;
    private final StatefulRedisConnection<String, String> redis;
    private final RealmLoader loader;
    private final VisitAnnouncer visitAnnouncer;

    public DefaultRealmVisitExecutor(PolarRealms api, StatefulRedisConnection<String, String> redis, RealmLoader loader) {
        this.api = api;
        this.redis = redis;
        this.loader = loader;
        this.visitAnnouncer = new VisitAnnouncer(redis);
        api.getEventBus().register(this);
    }


    @Override
    public CompletableFuture<Void> visit(RealmWorld realmWorld, OnlinePlayerHandle visitor) {
        return loader.withLoadedRealm(realmWorld).thenCompose((activeRealm) -> {
            if (api.getHostInformation().isEmpty()) { // Executor is not running on a host system => a player is always on the wrong server.
                return announceAndConnectVisitorToHostSystem(activeRealm, visitor);
            } else if (activeRealm.getHostServerName().equals(api.getHostInformation().get().getInternalName())) { // check realm is hosted on this system
                return visitor.getServerName().thenCompose(serverName -> {
                    if (serverName.equals(activeRealm.getHostServerName())) {
                        visitor.teleport(realmWorld.getProperties().getSpawnLocation()); // player is already connected to this host system and just has to be teleported.
                        return createCompletableOfPlayerIsConnectedOrTimeout(visitor.getUniqueId(), realmWorld); // return a future that will be completed when the player is connected to this host system
                    }
                    return announceAndConnectVisitorToHostSystem(activeRealm, visitor); // player is connected to this host system
                });
            } else { // realm is hosted elsewhere.
                return announceAndConnectVisitorToHostSystem(activeRealm, visitor);
            }
        });
    }


    /**
     * Announces the visitor to the host system and connects them to the realm.
     *
     * @param activeRealm the realm to visit
     * @param visitor     the player who wants to visit the realm
     * @return a future that completes when the visitor has been successfully connected to the realm.
     */
    protected CompletableFuture<Void> announceAndConnectVisitorToHostSystem(ActiveRealmSnapshot activeRealm, OnlinePlayerHandle visitor) {
        return visitAnnouncer.announce(visitor.getUniqueId(), activeRealm.getRealmId())
                .thenCompose((_void) -> visitor.connect(activeRealm.getHostServerName()))
                .thenCompose((_void) -> createCompletableOfPlayerIsConnectedOrTimeout(visitor.getUniqueId(), activeRealm.getRealm()));
    }

    /**
     * Creates a completable future that completes when the player is connected to the realm.
     *
     * @param playerUuid the UUID of the player to check.
     * @param realmWorld the realm world to check.
     * @return a completable future that completes when the player is connected to the realm.
     */
    protected CompletableFuture<Void> createCompletableOfPlayerIsConnectedOrTimeout(UUID playerUuid, RealmWorld realmWorld) {
        ActiveRealmSnapshot snapshot = getStateManager().getActiveRealmSnapshot(realmWorld.getRealmId()).orElseThrow(() -> new IllegalStateException("Realm must be loaded at this point."));
        if (snapshot.getOnlinePlayers().contains(playerUuid)) {
            return CompletableFuture.completedFuture(null);
        }
        return new PlayerConnectToRealmCallback(playerUuid, api.getEventBus(), 4).getFuture()
                .thenAccept(realmId -> {
                    if (realmId.equals(realmWorld.getRealmId())) {
                        api.getLogger().info("Player {} successfully connected to realm {}", playerUuid, realmWorld.getRealmId());
                    } else {
                        api.getLogger().warn("Player {} successfully connected to realm {}, but it was not the realm they wanted to visit.", playerUuid, realmWorld.getRealmId());
                    }
                });
    }

    public RealmStateManager getStateManager() {
        return api.getRealmStateManager();
    }


}
