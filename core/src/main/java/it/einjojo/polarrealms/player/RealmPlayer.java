package it.einjojo.polarrealms.player;

import it.einjojo.polarrealms.player.provider.NameProvider;
import it.einjojo.polarrealms.world.RealmWorld;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class RealmPlayer {
    private final UUID uuid;
    private final NameProvider nameProvider;

    public String getName() {
        return nameProvider.getUsername(uuid);
    }

    public List<RealmWorld> getRealms() {
        return List.of();
    }

    public List<String> getRealmAliases() {
        return List.of("-");
    }

}
