package it.einjojo.polarrealms.player;

import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.player.provider.NameProvider;
import it.einjojo.polarrealms.world.RealmWorld;
import lombok.Getter;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Stateless representation of a player.
 * <p>Used as a proxy object to access all player-related data.</p>
 */
@Getter
public class RealmPlayer {
    private final UUID uuid;
    private final PolarRealms api;

    public RealmPlayer(UUID uuid, PolarRealms api) {
        this.uuid = uuid;
        this.api = api;
    }

    public String getName() {
        return nameProvider().getUsername(uuid);
    }

    public Component getDisplayName() {
        return nameProvider().getDisplayName(uuid);
    }

    public CompletableFuture<List<RealmWorld>> getRealms() {
        return CompletableFuture.completedFuture(List.of());
    }

    public CompletableFuture<List<String>> getRealmAliases() {
        return CompletableFuture.completedFuture(List.of());
    }

    protected NameProvider nameProvider() {
        return api.getNameProvider();
    }


    public boolean hasReachedRealmLimit() {
        return false;
    }
}
