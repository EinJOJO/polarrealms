package it.einjojo.polarrealms.world;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.*;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public class RealmProperties {
    private final JsonObject internal;
    private final Gson gson;

    public RealmProperties(Gson gson) {
        this(new JsonObject(), gson);
    }

    public RealmProperties(JsonObject internal, Gson gson) {
        this.internal = internal;
        this.gson = gson;
    }

    public Location getSpawnLocation() {
        return gson.fromJson(internal.get("spawn"), Location.class);
    }

    public void setSpawnLocation(Location location) {
        internal.add("spawn", gson.toJsonTree(location));
    }

    public JsonElement get(String memberName) {
        return internal.get(memberName);
    }

    public boolean has(String memberName) {
        return internal.has(memberName);
    }

    public boolean isEmpty() {
        return internal.isEmpty();
    }

    public int size() {
        return internal.size();
    }

    public Set<String> keySet() {
        return internal.keySet();
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return internal.entrySet();
    }

    public void addProperty(String property, Character value) {
        internal.addProperty(property, value);
    }

    public void addProperty(String property, Boolean value) {
        internal.addProperty(property, value);
    }

    public void addProperty(String property, Number value) {
        internal.addProperty(property, value);
    }

    public void addProperty(String property, String value) {
        internal.addProperty(property, value);
    }

    @CanIgnoreReturnValue
    public JsonElement remove(String property) {
        return internal.remove(property);
    }

    public void add(String property, JsonElement value) {
        internal.add(property, value);
    }

    public JsonPrimitive getAsJsonPrimitive(String memberName) {
        return internal.getAsJsonPrimitive(memberName);
    }

    public JsonArray getAsJsonArray(String memberName) {
        return internal.getAsJsonArray(memberName);
    }

    public JsonObject getAsJsonObject(String memberName) {
        return internal.getAsJsonObject(memberName);
    }

    public Map<String, JsonElement> asMap() {
        return internal.asMap();
    }

    @Override
    public boolean equals(Object o) {
        return internal.equals(o);
    }

    @Override
    public int hashCode() {
        return internal.hashCode();
    }
}
