package it.einjojo.polarrealms.world;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import it.einjojo.polarrealms.exception.LockViolationException;
import it.einjojo.polarrealms.host.RealmHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

/**
 * Manages loaded realms using Redis and their locks.
 */
public class RealmStateManager {
    private static final int LOCK_TTL = 604800; // 7 days
    private static final String LOCKS = "pr:locks:";
    public static final String REALMS_HASH_MAP = "pr:realms:";
    public static final String REALM_STATE_FIELD = "state";
    private final StatefulRedisConnection<String, String> redis;

    public RealmStateManager(StatefulRedisConnection<String, String> redis) {
        this.redis = redis;
    }

    /**
     * Only called by the PaperRealmLoader
     * Updates the state in redis and dispatches a {@link it.einjojo.polarrealms.event.RealmStateChangeEvent}
     */
    protected void setState(ActiveRealmSnapshot snapshot, WorldState newState) {
        redis.sync().hset(REALMS_HASH_MAP + snapshot.getRealmId().toString(), REALM_STATE_FIELD, String.valueOf(newState.ordinal()));
    }

    /**
     * Claims a lock using redis atomic operations.
     *
     * @param realmId    the ID of the realm to lock
     * @param lockHolder the host that is claiming the lock
     * @throws LockViolationException if the lock is already claimed by another host
     */
    protected void claimLock(UUID realmId, RealmHost lockHolder) throws LockViolationException {
        if (!"OK".equals(redis.sync().set(LOCKS + realmId, lockHolder.getInternalName(), new SetArgs().nx().ex(LOCK_TTL)))) {
            throw new LockViolationException(realmId, lockHolder, redis.sync().get(LOCKS + realmId));
        }
    }

    /**
     * Releases a lock using redis atomic operations.
     *
     * @param realmId    id
     * @param lockHolder host that is releasing the lock
     * @throws LockViolationException if the lock is held by another host
     */
    protected void releaseLock(UUID realmId, RealmHost lockHolder) throws LockViolationException {
        String lockOwner = redis.sync().get(LOCKS + realmId);
        if (lockOwner == null) {
            getLogger().warn("Lock for realm {} is not claimed but was released.", realmId);
            return;
        }
        if (!lockOwner.equals(lockHolder.getInternalName())) {
            throw new LockViolationException(realmId, lockHolder, lockOwner);
        }
        redis.sync().del(LOCKS + realmId);
    }

    public Optional<ActiveRealmSnapshot> getActiveRealmSnapshot(UUID realmId) {
        return Optional.empty(); //TODO implement the logic
    }

    /**
     * Reads the state from redis
     *
     * @param uuid realm's UUID
     * @return the realm's state
     */
    public WorldState getRealmState(UUID uuid) {
        return WorldState.fromOrdinal(Integer.parseInt(redis.sync().hget(REALMS_HASH_MAP + uuid.toString(), REALM_STATE_FIELD)));
    }


    public Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }


}
