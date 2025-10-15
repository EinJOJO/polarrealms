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

/**
 * A realm world represents a single world in the game that is owned by a player.
 */
@Data
public class RealmWorld {

    public final UUID realmId;
    public final UUID ownerId;
    private final List<TrustedPlayer> trustedPlayers = new LinkedList<>();
    private String name;
    private long lastLoaded;
    private long createdAt;
    private final RealmProperties properties;
    private final PolarRealms api;


    /**
     * Visits the realm.
     *
     * @param visitor the player who wants to visit the realm
     */
    @CanIgnoreReturnValue
    public CompletableFuture<Void> visit(OnlinePlayerHandle visitor) {
        return null; //TODO implement visit executor
    }


}
