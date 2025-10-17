package it.einjojo.polarrealms.world.executor;


import com.google.common.eventbus.Subscribe;
import io.lettuce.core.api.StatefulRedisConnection;
import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.player.OnlinePlayerHandle;
import it.einjojo.polarrealms.world.ActiveRealmSnapshot;
import it.einjojo.polarrealms.world.RealmWorld;
import it.einjojo.polarrealms.world.loader.RealmLoader;
import it.einjojo.polarrealms.world.loader.RealmStateManager;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Getter
@NullMarked
public class DefaultRealmVisitExecutor implements RealmVisitExecutor {
    private final PolarRealms api;
    private final StatefulRedisConnection<String, String> redis;
    private final RealmLoader loader;
    private final List<CompletableFuture<Void>> realmConnectionFutures;

    public DefaultRealmVisitExecutor(PolarRealms api, StatefulRedisConnection<String, String> redis, RealmLoader loader) {
        this.api = api;
        this.redis = redis;
        this.loader = loader;
        api.getEventBus().register(this);
    }


    @Override
    public CompletableFuture<Void> visit(RealmWorld realmWorld, OnlinePlayerHandle visitor) {
        return withLoadedRealm().thenCompose((activeRealm) -> {
            if (api.getHostInformation().isEmpty()) { // Not a host system => a player always needs to be teleported.
                return announceAndConnectVisitorToHostSystem(activeRealm, visitor);
            } else if (activeRealm.getHostServerName().equals(api.getHostInformation().get().getInternalName())) { // realm is hosted on this system
                return visitor.getServerName().thenCompose(serverName -> {
                    if (serverName.equals(activeRealm.getHostServerName())) {
                        visitor.teleport(realmWorld.getProperties().getSpawnLocation());
                        return createCompletableOfPlayerIsConnectedOrTimeout(visitor.getUniqueId(), realmWorld);
                    } else {
                        return announceAndConnectVisitorToHostSystem(activeRealm, visitor);
                    }
                });
            }
        });
    }

    /**
     * Loads the realm asynchronously or returns an existing loaded realm.
     *
     * @return a CompletableFuture containing the loaded realm world
     */
    protected CompletableFuture<ActiveRealmSnapshot> withLoadedRealm() {
        return CompletableFuture.completedFuture(null);
    }

    protected CompletableFuture<Void> announceAndConnectVisitorToHostSystem(ActiveRealmSnapshot activeRealm, OnlinePlayerHandle visitor) {

    }

    protected CompletableFuture<Void> createCompletableOfPlayerIsConnectedOrTimeout(UUID playerUuid, RealmWorld realmWorld) {
        ActiveRealmSnapshot snapshot = getStateManager().getActiveRealmSnapshot(realmWorld.getRealmId()).orElseThrow(() -> new IllegalStateException("Realm must be loaded at this point."));
        if (snapshot.getOnlinePlayers().contains(playerUuid)) {
            return CompletableFuture.completedFuture(null);
        }


    }

    public RealmStateManager getStateManager() {
        return api.getRealmStateManager();
    }

    @Subscribe
    public void handleRealmConnection() {

    }


}
