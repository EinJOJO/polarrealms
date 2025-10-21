package it.einjojo.polarrealms.event.dispatch;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import it.einjojo.polarrealms.event.Event;
import lombok.Builder;
import lombok.Getter;

import java.util.Base64;
import java.util.function.Consumer;

/**
 * Lettuce implementation of the network event bus.
 * Obtain an instance using {@link #create(Consumer)}.
 */
public class LettuceNetworkEventBus extends NetworkEventBus {
    public static final String DEFAULT_CHANNEL = "pr:e";
    private final RedisClient redisClient;
    @Getter
    private final EventRegistry eventRegistry;
    private final String channel;
    private StatefulRedisPubSubConnection<String, String> connection;


    /**
     * Creates a new LettuceNetworkEventBus.
     *
     * @param redisClient The connection factory. It is the caller's responsibility to shut down the client after the last event has been posted to this event bus.
     */
    private LettuceNetworkEventBus(RedisClient redisClient, EventRegistry eventRegistry, String channel) {
        this.redisClient = redisClient;
        this.eventRegistry = eventRegistry;
        this.channel = channel;
    }

    /**
     * Creates a new LettuceNetworkEventBus that will subscribe to the given pub sub channel.
     *
     * @return a connected lettuce network event bus.
     */
    public static LettuceNetworkEventBus create(Consumer<Config.Builder> configEditor) {
        Config.Builder builder = Config.builder();
        configEditor.accept(builder);
        Config config = builder.build();
        if (config.getRedisClient() == null) {
            throw new IllegalArgumentException("RedisClient must not be null. Check your LettuceNetworkEventBus configuration");
        }
        LettuceNetworkEventBus bus = new LettuceNetworkEventBus(config.getRedisClient(), config.getEventRegistry(), config.getChannel());
        bus.connect();
        return bus;
    }

    /**
     * Connects to the pub sub channel.
     */
    private void connect() {
        //TODO Connect to pub sub channel
    }


    @Override
    public void post(Object event) {
        if (connection == null) {
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
    }


    @Builder(builderClassName = "Builder")
    @Getter
    public static class Config {
        @lombok.Builder.Default
        private String channel = DEFAULT_CHANNEL;
        private RedisClient redisClient;
        @lombok.Builder.Default
        private EventRegistry eventRegistry = new EventRegistry();
    }

}
