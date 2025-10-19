package net.wandoria.polarrealms;

import it.einjojo.playerapi.PlayerApi;
import it.einjojo.playerapi.PlayerApiProvider;
import it.einjojo.polarrealms.player.OnlinePlayerHandle;
import it.einjojo.polarrealms.player.OnlinePlayerHandleFactory;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WandoriaOnlinePlayerHandleFactory implements OnlinePlayerHandleFactory {
    private final PlayerApi playerApi;

    public WandoriaOnlinePlayerHandleFactory() {
        this.playerApi = PlayerApiProvider.getInstance();
    }

    @Override
    public OnlinePlayerHandle createHandle(UUID uuid) throws IllegalStateException {
        return new WandoriaOnlinePlayerHandle(uuid, playerApi);
    }

    @Override
    public OnlinePlayerHandle createHandle(Object platformPlayerObject) throws IllegalStateException {
        if (platformPlayerObject instanceof Player player) {
            return new WandoriaOnlinePlayerHandle(player.getUniqueId(), playerApi);
        }
        throw new IllegalStateException("Unsupported platform player object. Must be instance of Bukkit Player");
    }
}
