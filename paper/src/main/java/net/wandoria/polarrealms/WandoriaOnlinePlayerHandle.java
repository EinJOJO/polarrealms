package net.wandoria.polarrealms;

import it.einjojo.playerapi.PlayerApi;
import it.einjojo.polarrealms.player.OnlinePlayerHandle;
import it.einjojo.polarrealms.util.BukkitAdapter;
import it.einjojo.polarrealms.world.Location;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public record WandoriaOnlinePlayerHandle(UUID uniqueId, PlayerApi playerApi) implements OnlinePlayerHandle {

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public CompletableFuture<Void> connect(String velocityServerName) {
        playerApi.connectPlayerToServer(uniqueId, velocityServerName);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void connectSilently(String velocityServerName) {
        connect(velocityServerName);
    }

    @Override
    public CompletableFuture<String> getServerName() {
        return playerApi.getOnlinePlayer(uniqueId).thenApply(onlinePlayer -> {
            if (onlinePlayer != null) {
                return onlinePlayer.getConnectedServerName().orElse(null);
            }
            throw new IllegalStateException("Player with id " + uniqueId + " is not an online player in the wandoria player-api");
        });
    }

    @Override
    public void teleport(Location location) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) {
            throw new IllegalStateException("Player with id " + uniqueId + " is not online on this server");
        }
        player.teleport(BukkitAdapter.fromLocation(location));
    }
}
