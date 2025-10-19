package it.einjojo.polarrealms.event;

import lombok.Data;

import java.util.UUID;

@Data
public class RealmPlayerEnterEvent {
    private final UUID playerId;
    private final UUID realmId;
}
