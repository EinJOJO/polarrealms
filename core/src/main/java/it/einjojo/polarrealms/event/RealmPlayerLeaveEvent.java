package it.einjojo.polarrealms.event;

import it.einjojo.polarrealms.event.dispatch.NetworkEvent;

@NetworkEvent(id = 1)
public class RealmPlayerLeaveEvent {

    public static RealmPlayerLeaveEvent deserialize(byte[] bytes) {
        return new RealmPlayerLeaveEvent();
    }
}
