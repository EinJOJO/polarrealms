package it.einjojo.polarrealms.world;

import org.jspecify.annotations.Nullable;

import java.util.UUID;

public record Location(float x, float y, float z, float yaw, float pitch, @Nullable UUID realmId) {


}
