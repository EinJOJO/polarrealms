package it.einjojo.polarrealms.player;

import it.einjojo.polarrealms.player.provider.NameProvider;
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
public class TrustedPlayer {
    private final UUID uuid;
    private final RealmWorld realmWorld;
    private final NameProvider nameProvider;

    public TrustedPlayer(UUID uuid, RealmWorld realmWorld, NameProvider nameProvider) {
        this.uuid = uuid;
        this.realmWorld = realmWorld;
        this.nameProvider = nameProvider;
    }

    public String getName() {
        return nameProvider.getUsername(uuid);
    }

    public boolean isTrusted() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void remove() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
