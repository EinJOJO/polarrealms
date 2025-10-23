package it.einjojo.polarrealms.player;

import it.einjojo.polarrealms.player.provider.NameProvider;
import it.einjojo.polarrealms.world.RealmWorld;
import lombok.Getter;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.UUID;

/**
 * @see PlayerService
 */
@Getter
public class RealmPlayer {
    private final UUID uuid;
    private final NameProvider nameProvider;

    public RealmPlayer(UUID uuid, NameProvider nameProvider) {
        this.uuid = uuid;
        this.nameProvider = nameProvider;
    }

    public String getName() {
        return nameProvider.getUsername(uuid);
    }

    public Component getDisplayName() {
        return nameProvider.getDisplayName(uuid);
    }

    public List<RealmWorld> getRealms() {
        return List.of();
    }

    public List<String> getRealmAliases() {
        return List.of("-");
    }


}
