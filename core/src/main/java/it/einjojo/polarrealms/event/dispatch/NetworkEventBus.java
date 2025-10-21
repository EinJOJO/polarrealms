package it.einjojo.polarrealms.event.dispatch;


import com.google.common.eventbus.EventBus;
import lombok.Getter;

/**
 * Dispatches events to all servers in the network.
 *
 */
public abstract class NetworkEventBus {
    @Getter
    protected final EventBus eventBus = new EventBus("networkBusInternal");

    public abstract void post(Object event);

    /**
     * Returns the event registry that is used by this event bus.
     *
     * @return event registry
     */
    public abstract EventRegistry getEventRegistry();

    /**
     * Proxies to internal guava event bus instance
     *
     * @param guavaListener listener to register
     */
    public void register(Object guavaListener) {
        eventBus.register(guavaListener);
    }

    /**
     * Proxies to internal guava event bus instance
     *
     * @param guavaListener listener to unregister
     */
    public void unregister(Object guavaListener) {
        eventBus.unregister(guavaListener);
    }


    public EventBus getInternalEventBus() {
        return eventBus;
    }
}
