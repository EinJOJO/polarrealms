package it.einjojo.polarrealms.world;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ActiveRealmSnapshot {
    private final RealmWorld realm;
    private final WorldState state;
    private final List<UUID> onlinePlayers;
    /**
     * The server which owns the realm-lock in redis.
     */
    private final String hostServerName;


}
