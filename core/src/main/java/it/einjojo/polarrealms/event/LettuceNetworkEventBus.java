package it.einjojo.polarrealms.event;

import io.lettuce.core.RedisClient;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * Lettuce implementation of the network event bus.
 */
public class LettuceNetworkEventBus extends NetworkEventBus {
    public static final String DEFAULT_CHANNEL = "pr:e";
    private final RedisClient redisClient;
    @Getter
    private final EventRegistry eventRegistry;


    /**
     * Creates a new LettuceNetworkEventBus.
     *
     * @param redisClient The connection factory. It is the caller's responsibility to shut down the client after the last event has been posted to this event bus.
     */
    private LettuceNetworkEventBus(RedisClient redisClient, EventRegistry eventRegistry) {
        this.redisClient = redisClient;
        this.eventRegistry = eventRegistry;
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
        LettuceNetworkEventBus bus = new LettuceNetworkEventBus(config.getRedisClient(), config.getEventRegistry());
        bus.connect();
        return bus;
    }

    /**
     * Connects to the pub sub channel.
     */
    public void connect() {
        //TODO Connect to pub sub channel
    }


    @Override
    public void post(Object event) {
        //TODO Post event to pub sub channel
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
