package it.einjojo.polarrealms.world.template;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TemplateSetup {
    @Getter
    private final UUID playerId;
    private Player player;

    public TemplateSetup(Player player) {
        this.playerId = player.getUniqueId();
        this.player = player;
    }

}
