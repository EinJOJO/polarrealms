package it.einjojo.polarrealms.event.dispatch;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import it.einjojo.polarrealms.event.Event;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.Base64;
import java.util.function.Consumer;

/**
 * Lettuce implementation of the network event bus.
 * Obtain an instance using {@link #create(Consumer)}.
 */
@Slf4j
public class LettuceNetworkEventBus extends NetworkEventBus implements Closeable {
    public static final String DEFAULT_CHANNEL = "pr:e";
    private final RedisClient redisClient;
    @Getter
    private final EventRegistry eventRegistry;
    private final String channel;
    private StatefulRedisPubSubConnection<String, String> connection;
    @Getter
    private boolean closing;


    /**
     * Creates a new LettuceNetworkEventBus.
     *
     * @param redisClient The connection factory. It is the caller's responsibility to shut down the client after the last event has been posted to this event bus.
     */
    private LettuceNetworkEventBus(@NonNull RedisClient redisClient, @NonNull EventRegistry eventRegistry, @NonNull String channel) {
        this.redisClient = redisClient;
        this.eventRegistry = eventRegistry;
        this.channel = channel;
    }

    /**
     * Creates a new LettuceNetworkEventBus that will subscribe to the given pub sub channel.
     * The Builder can be used to configure the event bus.
     * <p>You must provide a {@link RedisClient} </p>
     *
     * @return a connected lettuce network event bus.
     */
    public static LettuceNetworkEventBus create(Consumer<Config.Builder> builderConsumer) {
        Config.Builder builder = Config.builder();
        builderConsumer.accept(builder);
        final Config config = builder.build();
        LettuceNetworkEventBus bus = new LettuceNetworkEventBus(config.getRedisClient(), config.getEventRegistry(), config.getChannel());
        bus.connect();
        return bus;
    }

    /**
     * Connects to the pub sub channel.
     */
    private void connect() {
        if (connection != null) {
            throw new IllegalStateException("Already connected to a pub sub channel");
        }
        this.connection = redisClient.connectPubSub();
        connection.sync().subscribe(channel);
        closing = false;
        log.info("Connected to pub sub channel {}", channel);
    }


    @Override
    public void post(Object event) {
        if (isClosed()) {
            throw new IllegalStateException("Not connected to a pub sub channel");
        }
        if (!(event instanceof Event serializableEvent)) {
            throw new IllegalArgumentException("Event must be an instance of Event");
        }
        Byte eventId = eventRegistry.getEventId(event.getClass());
        if (eventId == null) {
            throw new IllegalStateException("Event " + event.getClass().getSimpleName() + " is not registered to the underlying event registry");
        }
        String payload = Base64.getEncoder().encodeToString(serializableEvent.createPayload());
        connection.async().publish(channel, eventId + ";" + payload);
        log.debug("Posted event {} to pub sub channel {}", event.getClass().getSimpleName(), channel);
    }

    @Override
    public void close() {
        closing = true;
        if (connection != null) {
            connection.close();
            log.info("Closed pub sub channel {}", channel);
            connection = null;
        }
    }

    public boolean isClosed() {
        return connection == null;
    }


    @Builder(builderClassName = "Builder")
    @Getter
    public static class Config {
        @lombok.Builder.Default
        private String channel = DEFAULT_CHANNEL;
        @NonNull
        private RedisClient redisClient;
        @lombok.Builder.Default
        @NonNull
        private EventRegistry eventRegistry = new EventRegistry();
    }

}
