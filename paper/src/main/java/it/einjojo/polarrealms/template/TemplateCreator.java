package it.einjojo.polarrealms.template;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class TemplateCreator {
    private static final Component PREFIX = MiniMessage.miniMessage().deserialize("<#f9a8d4><b>ᴄʀᴇᴀᴛᴏʀ</b></#f9a8d4><#a5b4fc>");
    protected final MiniMessage miniMessage = MiniMessage.builder()
            .editTags(builder -> {
                builder.tag("creator", Tag.inserting(PREFIX));
            })
            .build();
    @Getter
    private final UUID playerId;
    private final Player player;
    private final _2SpawnSelection spawn = setupStage(new _2SpawnSelection(), null);
    private final _1WorldSelection world = setupStage(new _1WorldSelection(), spawn);
    @Getter
    private Stage currentStage = world;


    public TemplateCreator(Player player) {
        this.playerId = player.getUniqueId();
        this.player = player;
        start();
    }

    private void start() {
        player.sendMessage(miniMessage.deserialize("<creator> Willkommen im Setup Modus"));
        player.sendMessage(miniMessage.deserialize("<creator> Die Eingabe wird mit /realms template setup '<eingabe>' verarbeitet."));
        printInstructions();
    }

    public void next(@Nullable String input) {
        var stage = getCurrentStage();
        if (stage == null) return;
        try {
            if (stage.handleInput(player, input)) {
                currentStage = stage.getNextStage();
                printInstructions();
            }
        } catch (Exception e) {
            player.sendMessage(miniMessage.deserialize("<prefix> Es ist ein Fehler aufgetreten: " + e.getMessage()));
        }
    }

    /**
     * Setup a stage and set the next stage.
     *
     * @param stage     the stage to setup
     * @param nextStage the next stage to set
     * @param <T>       the type of the stage
     * @return the setup stage
     */
    private <T extends Stage> T setupStage(T stage, Stage nextStage) {
        stage.setMiniMessage(Objects.requireNonNull(miniMessage, "MiniMessage must not be null!"));
        stage.setNextStage(nextStage);
        return stage;
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

}
