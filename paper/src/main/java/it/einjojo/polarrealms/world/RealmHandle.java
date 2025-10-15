package it.einjojo.polarrealms.world;

import it.einjojo.polarrealms.world.loader.PaperRealmLoader;
import live.minehub.polarpaper.PolarWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.UUID;

public class RealmHandle {
    private final RealmWorld parent;
    private final PolarWorld polarWorld;
    private final PaperRealmLoader loader;

    public RealmHandle(RealmWorld parent, PolarWorld polarWorld, PaperRealmLoader loader) {
        this.parent = parent;
        this.polarWorld = polarWorld;
        this.loader = loader;
    }

    public UUID getRealmId() {
        return parent.getRealmId();
    }

    public RealmWorld getRealmWorld() {
        return parent;
    }

    public World getBukkitWorld() {
        World world = Bukkit.getWorld(getRealmId());
        if (world == null) {
            throw new IllegalStateException("World with id " + getRealmId() + " is not loaded.");
        }
        return world;
    }
}
