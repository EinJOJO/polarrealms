package it.einjojo.polarrealms.world;

import java.util.UUID;

public class RealmHandle {
    private final RealmWorld parent;

    public RealmHandle(RealmWorld parent) {
        this.parent = parent;

    }

    public UUID getRealmId() {
        return parent.getRealmId();
    }

    public RealmWorld getRealmWorld() {
        return parent;
    }
}
