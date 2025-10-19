package it.einjojo.polarrealms.util;

import org.bukkit.Location;

public class BukkitAdapter {

    public static Location fromLocation(it.einjojo.polarrealms.world.Location polarRealmsLocation) {
        return new Location(null, polarRealmsLocation.x(), polarRealmsLocation.y(), polarRealmsLocation.z(), polarRealmsLocation.yaw(), polarRealmsLocation.pitch());
    }

}
