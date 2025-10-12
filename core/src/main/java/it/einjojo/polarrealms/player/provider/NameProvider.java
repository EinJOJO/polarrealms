package it.einjojo.polarrealms.player.provider;

import java.util.UUID;

/**
 *
 */
public interface NameProvider {


    String getUsername(UUID playerId);

}
