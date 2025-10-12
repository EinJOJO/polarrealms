package it.einjojo.polarrealms.world;

import it.einjojo.polarrealms.player.OnlinePlayerHandle;
import it.einjojo.polarrealms.player.TrustedPlayer;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * A realm world represents a single world in the game that is owned by a player.
 */
@Getter
public class RealmWorld {

    public final UUID realmId;
    public final UUID ownerId;
    private final List<TrustedPlayer> trustedPlayers = new LinkedList<>();
    private long lastLoaded;
    private long createdAt;

    /**
     *
     * @param visitor
     */
    public void visit(OnlinePlayerHandle visitor) {

    }


}
