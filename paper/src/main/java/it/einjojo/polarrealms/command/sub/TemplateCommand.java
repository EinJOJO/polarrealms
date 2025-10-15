package it.einjojo.polarrealms.command.sub;

import it.einjojo.polarrealms.world.template.TemplateSetup;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.jspecify.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@CommandContainer
public class TemplateCommand {
    private final List<TemplateSetup> templateSetups = new LinkedList<>();

    public TemplateCommand() {
    }


    @Command("realm|realms template setup")
    public void templateSetup(PlayerSource playerSource) {

    }

    public @Nullable TemplateSetup getSetup(UUID playerId) {
        for (TemplateSetup setup : templateSetups) {
            if (setup.getPlayerId().equals(playerId)) {
                return setup;
            }
        }
        return null;
    }

}
