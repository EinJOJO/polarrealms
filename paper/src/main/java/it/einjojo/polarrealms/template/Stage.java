package it.einjojo.polarrealms.template;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.List;


@Getter
@Setter
abstract class Stage {

    protected MiniMessage miniMessage = MiniMessage.miniMessage();

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


}
