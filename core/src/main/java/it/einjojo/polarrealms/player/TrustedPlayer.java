package it.einjojo.polarrealms.player;

import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.world.RealmWorld;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

/**
 * Trusted Player belongs to a realm world.
 *
 * @see RealmWorld#getTrustedPlayers()
 */
@Getter
@NullMarked
public class TrustedPlayer extends RealmPlayer {
    private final UUID uuid;
    private final RealmWorld realmWorld;

    public TrustedPlayer(UUID uuid, RealmWorld realmWorld, PolarRealms api) {
        super(uuid, api);
        this.uuid = uuid;
        this.realmWorld = realmWorld;
    }


    public boolean isTrusted() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void remove() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
