package it.einjojo.polarrealms.player;

import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.player.provider.NameProvider;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;

/**
 * Get instances of {@link RealmPlayer}
 */
@NullMarked

public class PlayerService {
    @Getter
    private final PolarRealms api;
    @Getter
    @Setter
    private NameProvider nameProvider;


    public PlayerService(PolarRealms api) {
        this.api = api;
    }
}
