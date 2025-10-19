package it.einjojo.polarrealms.world.executor;

import io.lettuce.core.api.StatefulRedisConnection;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class VisitAnnouncer {
    private final StatefulRedisConnection<String, String> redis;
    public static final String VISIT_ANNOUNCE_KEY = "pr:visit:announce:";
    public static int VISIT_ANNOUNCE_TTL_SECONDS = 5;


    public VisitAnnouncer(StatefulRedisConnection<String, String> redis) {
        this.redis = redis;
    }

    public CompletableFuture<Void> announce(UUID playerId, UUID realmId) {
        return redis.async().setex(VISIT_ANNOUNCE_KEY + playerId, VISIT_ANNOUNCE_TTL_SECONDS, realmId.toString())
                .toCompletableFuture().thenApply((result) -> null);
    }

    public CompletableFuture<Optional<UUID>> getAnnouncedRealm(UUID playerId) {
        return redis.async().get(VISIT_ANNOUNCE_KEY + playerId)
                .toCompletableFuture().thenApply((result) -> Optional.ofNullable(result).map(UUID::fromString));
    }
}
