package it.einjojo.polarrealms.world;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.player.OnlinePlayerHandle;
import it.einjojo.polarrealms.player.TrustedPlayer;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

/**
 * A realm world represents a single world in the game that is owned by a player.
 */
@Data
public class RealmWorld {
    public static Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{1,64}$");
    public final UUID realmId;
    public final UUID ownerId;
    private final List<TrustedPlayer> trustedPlayers = new LinkedList<>();
    private String name;
    private long lastLoaded;
    private long createdAt;
    private final RealmProperties properties;
    private final PolarRealms api;

    public RealmWorld(UUID realmId, UUID ownerId, String name, long lastLoaded, long createdAt, RealmProperties properties, PolarRealms api) {
        this.realmId = realmId;
        this.ownerId = ownerId;
        this.name = name;
        this.lastLoaded = lastLoaded;
        this.createdAt = createdAt;
        this.properties = properties;
        this.api = api;
    }

    /**
     * Visits the realm.
     *
     * @param visitor the player who wants to visit the realm
     */
    @CanIgnoreReturnValue
    public CompletableFuture<Void> visit(OnlinePlayerHandle visitor) {
        return api.getVisitExecutor().visit(this, visitor);
    }

    public void setName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Invalid realm name format. Must be alphanumeric and less than 64 characters long.");
        }
        this.name = name;
    }
}
