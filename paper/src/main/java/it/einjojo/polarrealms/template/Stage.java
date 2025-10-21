package it.einjojo.polarrealms.template;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.List;


@Getter
abstract class Stage {
    @Setter
    protected MiniMessage miniMessage = MiniMessage.miniMessage();
    protected @Nullable Stage nextStage;
    protected @Nullable Stage previousStage;

    /**
     * Checks if the stage is completed.
     * Used for moving back and forth between stages
     *
     * @return true if the stage is completed and false otherwise
     */
    abstract boolean isDone();

    /**
     * Returns a list of suggestions for the player.
     *
     * @param player the player to get suggestions for
     * @return a list of suggestions for the player
     */
    abstract List<String> suggestions(Player player);

    /**
     * Handles the input of the player.
     *
     * @return true if the input was handled and false otherwise
     */
    abstract boolean handleInput(Player player, String input);

    abstract void printInstructions(Player player);

    /**
     * Sets the next stage and updates the previous stage of the provided object, making a linear chain of stages.
     *
     * @param nextStage the next stage to set
     *
     */
    public void setNextStage(@Nullable Stage nextStage) {
        if (nextStage != null && nextStage.previousStage != null)
            throw new IllegalArgumentException("Previous stage must be null!");
        if (nextStage != null) {
            nextStage.previousStage = this;
        }
        this.nextStage = nextStage;
    }
}
