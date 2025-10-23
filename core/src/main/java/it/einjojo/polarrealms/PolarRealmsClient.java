package it.einjojo.polarrealms;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import it.einjojo.polarrealms.event.dispatch.LettuceNetworkEventBus;
import it.einjojo.polarrealms.host.RealmHost;
import it.einjojo.polarrealms.player.OnlinePlayerHandleFactory;
import it.einjojo.polarrealms.player.RealmPlayer;
import it.einjojo.polarrealms.world.executor.DefaultRealmVisitExecutor;
import it.einjojo.polarrealms.world.executor.RealmVisitExecutor;
import it.einjojo.polarrealms.world.loader.RealmLoader;
import it.einjojo.polarrealms.world.loader.RealmStateManager;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Use this class if you want to create an instance of {@link PolarRealms} on your platform implementation.
 */
@Getter
@Setter
public class PolarRealmsClient implements PolarRealms {
    private final RedisClient redis;
    private final RealmLoader loader;
    private final RealmStateManager realmStateManager;
    private RealmVisitExecutor realmVisitExecutor;


    /**
     *
     * @param redis  used for creating a connection.
     * @param loader the loader responsible for loading realm worlds on the platform (either common-RPC or local host loader implementation).
     */
    public PolarRealmsClient(@NonNull RedisClient redis, @NonNull RealmLoader loader) {
        this.redis = redis;
        this.loader = loader;
        StatefulRedisConnection<String, String> connection = redis.connect();
        this.realmStateManager = new RealmStateManager(connection);
        this.realmVisitExecutor = new DefaultRealmVisitExecutor(this, connection, loader);
    }


    @Override
    public CompletableFuture<Optional<RealmPlayer>> getPlayer(UUID playerId) {
        return null;
    }

    @Override
    public RealmVisitExecutor getVisitExecutor() {
        return null;
    }

    @Override
    public OnlinePlayerHandleFactory getOnlinePlayerHandleFactory() {
        return null;
    }

    @Override
    public RealmStateManager getRealmStateManager() {
        return null;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public Optional<RealmHost> getHostInformation() {
        return Optional.empty();
    }

    @Override
    public LettuceNetworkEventBus getEventBus() {
        return null;
    }


}
