package it.einjojo.polarrealms.event.dispatch;

import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * In the event registry we store all events that can be dispatched with an id and their deserializer.
 */
public class EventRegistry {
    private final ConcurrentHashMap<Byte, Function<byte[], ?>> deserializers = new ConcurrentHashMap<>();
    private final Map<Byte, Class<?>> eventIds = new ConcurrentHashMap<>();
    private final Map<Class<?>, Byte> eventClasses = new ConcurrentHashMap<>();


    public EventRegistry() {
    }

    /**
     * Bind an event class to an id and a deserializer.
     *
     * @param clazz        the event class to bind.
     * @param eventId      the event id that should be used for dispatching.
     * @param deserializer the deserializer to bind.
     * @param <T>          event class
     */
    public <T> void bind(Class<T> clazz, byte eventId, Function<byte[], Object> deserializer) {
        if (eventId < 0) throw new IllegalArgumentException("Event id cannot be negative.");
        if (eventIds.containsKey(eventId)) throw new IllegalArgumentException("Event id already registered.");
        eventIds.put(eventId, clazz);
        eventClasses.put(clazz, eventId);
        deserializers.put(eventId, deserializer);
    }

    /**
     * Get the deserializer for a given event id.
     *
     * @param eventId the event id.
     * @return the deserializer function.
     */
    public Function<byte[], ?> getDeserializer(int eventId) {
        return deserializers.get(eventId);
    }

    public @Nullable Byte getEventId(Class<?> clazz) {
        return eventClasses.get(clazz);
    }


    public Object deserialize(int eventId, byte[] payload) {
        return getDeserializer(eventId).apply(payload);
    }

    public Collection<Class<?>> getRegisteredEvents() {
        return Collections.unmodifiableCollection(eventClasses.keySet());
    }

}
