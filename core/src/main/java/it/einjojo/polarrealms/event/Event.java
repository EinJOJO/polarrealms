package it.einjojo.polarrealms.event;

/**
 * A realm event can be dispatched network wide.
 */
public abstract class Event {

    public abstract byte[] createPayload();

}
