package it.einjojo.polarrealms.world.template;

import it.einjojo.polarrealms.PaperPolarRealms;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


public class TemplateCreator {
    private final MiniMessage miniMessage = PaperPolarRealms.miniMessage();
    @Getter
    private final UUID playerId;
    private Player player;


    public TemplateCreator(Player player) {
        this.playerId = player.getUniqueId();
        this.player = player;
        start();
    }

    private void start() {
        player.sendMessage(miniMessage.deserialize("<prefix> Willkommen im Setup Modus"));
        player.sendMessage(miniMessage.deserialize("<prefix> Die Eingabe wird mit /realms template setup '<eingabe>' verarbeitet."));

    }

    public void next(String input) {

    }

    public List<String> getSuggestion() {
        return List.of("creatorsuggestion");
    }
}
