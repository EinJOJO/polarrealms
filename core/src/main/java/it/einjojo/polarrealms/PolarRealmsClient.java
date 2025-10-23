package it.einjojo.polarrealms;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import it.einjojo.polarrealms.event.dispatch.EventRegistry;
import it.einjojo.polarrealms.event.dispatch.LettuceNetworkEventBus;
import it.einjojo.polarrealms.event.dispatch.NetworkEventBus;
import it.einjojo.polarrealms.host.RealmHost;
import it.einjojo.polarrealms.player.OnlinePlayerHandleFactory;
import it.einjojo.polarrealms.player.PlayerService;
import it.einjojo.polarrealms.util.ShutdownHook;
import it.einjojo.polarrealms.world.executor.DefaultRealmVisitExecutor;
import it.einjojo.polarrealms.world.executor.RealmVisitExecutor;
import it.einjojo.polarrealms.world.loader.RealmLoader;
import it.einjojo.polarrealms.world.loader.RealmStateManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * Use this class if you want to create an instance of {@link PolarRealms} on your platform implementation.
 * <p>All Setters are safe to use during runtime to modify the behavior of the realm system.</p>
 */
@Getter
@Setter
public class PolarRealmsClient implements PolarRealms {
    private final EventRegistry eventRegistry = new EventRegistry();
    /**
     * Run logic before services are shutdown (like the EventBus)
     */
    @Setter(AccessLevel.NONE)
    private ShutdownHook shutdownHooks = null;
    private final RedisClient redis;
    private final RealmLoader loader;
    private final Logger logger;
    private final RealmStateManager realmStateManager;
    private final OnlinePlayerHandleFactory onlinePlayerHandleFactory;
    private RealmVisitExecutor visitExecutor;
    private PlayerService playerService;
    private NetworkEventBus eventBus;

    /**
     * <i>Can only be set by the paper module</i>
     */
    @Setter(AccessLevel.PACKAGE)
    private RealmHost hostInformation = null;


    /**
     * Creates a new instance of {@link PolarRealmsClient}.
     * <p>Constructs default instances of {@link RealmStateManager}, {@link RealmVisitExecutor}, {@link PlayerService}
     * and uses {@link LettuceNetworkEventBus} for the event bus.</p>
     *
     * @param redis                     used for creating a redis connection.
     * @param loader                    Either {@link it.einjojo.polarrealms.world.loader.RealmLoaderRPC} or the local host loader implementation
     * @param logger                    Used for logging.
     * @param onlinePlayerHandleFactory Creates online player handles.
     */
    public PolarRealmsClient(@NonNull RedisClient redis,
                             @NonNull RealmLoader loader,
                             @NonNull Logger logger,
                             @NonNull OnlinePlayerHandleFactory onlinePlayerHandleFactory) {
        this.redis = redis;
        this.loader = loader;
        this.logger = logger;
        this.onlinePlayerHandleFactory = onlinePlayerHandleFactory;
        StatefulRedisConnection<String, String> connection = redis.connect();
        this.realmStateManager = new RealmStateManager(connection);
        this.visitExecutor = new DefaultRealmVisitExecutor(this, connection, loader);
        this.playerService = new PlayerService();
        this.eventBus = createNetworkEventBus();
    }

    /**
     * Registers a shutdown hook.
     *
     * @param runnable runnable to register
     */
    public void registerShutdownHook(@NonNull Runnable runnable, @NonNull String description) {
        // append to the linked structure
        this.shutdownHooks = new ShutdownHook(
                runnable,
                description,
                shutdownHooks,
                shutdownHooks == null ? 0 : shutdownHooks.remaining() + 1);

    }

    /**
     * Calls the registered shutdown hooks.
     */
    public void shutdown() {
        getLogger().info("Shutting down...");
        shutdownHooks.execute(this);
        eventBus.close();
    }

    /**
     * Overwrite this method to use custom implementation of {@link NetworkEventBus}.
     *
     * @return an instance of {@link NetworkEventBus}
     */
    protected NetworkEventBus createNetworkEventBus() {
        return LettuceNetworkEventBus.create((builder) -> builder
                .redisClient(redis)
                .eventRegistry(eventRegistry)
        );
    }

    public Optional<RealmHost> getHostInformation() {
        return Optional.ofNullable(hostInformation);
    }
}
