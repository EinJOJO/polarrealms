package it.einjojo.polarrealms.world;

import it.einjojo.polarrealms.world.loader.PaperRealmLoader;
import live.minehub.polarpaper.PolarWorld;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.UUID;

/**
 * A realm handle is only available on the host system and is a loaded realm.
 *
 * @see ActiveRealmSnapshot
 * @see RealmWorld
 */
@Getter
public class RealmHandle {
    private final RealmWorld parent;
    private final PolarWorld polarWorld;
    private final PaperRealmLoader loader;
    /**
     * Changing the state in the handle will execute the corresponding action.
     * This is different from the data state managed in {@link RealmStateManager}.
     */
    @Setter
    private WorldState state;
    private final String hostServerName;

    public RealmHandle(RealmWorld parent, PolarWorld polarWorld, PaperRealmLoader loader, String hostServerName) {
        this.parent = parent;
        this.polarWorld = polarWorld;
        this.loader = loader;
        this.hostServerName = hostServerName;
    }

    public UUID getRealmId() {
        return parent.getRealmId();
    }

    public RealmWorld getRealmWorld() {
        return parent;
    }

    public ActiveRealmSnapshot createSnapshot() {
        List<UUID> onlinePlayers = getBukkitWorld().getPlayers().stream().map(Entity::getUniqueId).toList();
        return new ActiveRealmSnapshot(parent, state, onlinePlayers, hostServerName);
    }

    public World getBukkitWorld() {
        World world = Bukkit.getWorld(getRealmId());
        if (world == null) {
            throw new IllegalStateException("World with id " + getRealmId() + " is not loaded.");
        }
        return world;
    }
}
