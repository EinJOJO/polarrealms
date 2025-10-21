package it.einjojo.polarrealms.template;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TemplateCreator {
    @Getter
    private final UUID playerId;
    private final Player player;
    private final ArrayList<Stage> stages = new ArrayList<>(List.of(
            new _1WorldSelection()
    ));
    private int currentStage = 0;
    private final MiniMessage miniMessage = MiniMessage.builder()
            .editTags(builder -> {
                builder.tag("creator", this::apply);
            })
            .build();


    public TemplateCreator(Player player) {
        this.playerId = player.getUniqueId();
        this.player = player;
        start();
    }

    private Tag apply(ArgumentQueue argumentQueue, Context context) {
        return Tag.inserting(Component.text());
    }

    private void start() {
        player.sendMessage(miniMessage.deserialize("<prefix> Willkommen im Setup Modus"));
        player.sendMessage(miniMessage.deserialize("<prefix> Die Eingabe wird mit /realms template setup '<eingabe>' verarbeitet."));
        printInstructions();
    }

    public void next(String input) {
        var stage = getCurrentStage();
        if (stage == null) return;
        try {
            if (stage.handleInput(player, input)) {
                currentStage++;
                printInstructions();
            }
        } catch (Exception e) {
            player.sendMessage(miniMessage.deserialize("<prefix> Es ist ein Fehler aufgetreten: " + e.getMessage()));
        }
    }


    void printInstructions() {
        if (getCurrentStage() == null) return;
        getCurrentStage().printInstructions(player);
        printNavigationBar();
    }

    private void printNavigationBar() {

    }

    public List<String> getSuggestion() {
        var stage = getCurrentStage();
        if (stage == null) return List.of();
        return stage.suggestions(player);
    }

    private Stage getCurrentStage() {
        if (currentStage < 0 || currentStage >= stages.size()) {
            return null;
        }
        return stages.get(currentStage);
    }
}
