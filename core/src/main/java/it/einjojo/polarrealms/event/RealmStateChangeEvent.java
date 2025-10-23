package it.einjojo.polarrealms.event;

import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.event.dispatch.NetworkEvent;
import it.einjojo.polarrealms.world.WorldState;
import it.einjojo.polarrealms.world.RealmStateManager;

import java.util.UUID;

@NetworkEvent(id = 3)
public class RealmStateChangeEvent {
    private final UUID realmId;

    public RealmStateChangeEvent(UUID realmId) {
        this.realmId = realmId;
    }

    /**
     * Get the state of the realm
     *
     * @param stateManager obtain with {@link PolarRealms#getRealmStateManager()}
     * @return the state of the realm
     */
    public WorldState getState(RealmStateManager stateManager) {
        return stateManager.getRealmState(realmId);
    }
}
