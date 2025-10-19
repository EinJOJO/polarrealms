package it.einjojo.polarrealms.command.sub;

import it.einjojo.polarrealms.template.Template;
import it.einjojo.polarrealms.world.template.TemplateCreator;
import lombok.extern.slf4j.Slf4j;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.jspecify.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
@CommandContainer
public class TemplateCommand {
    private final List<TemplateCreator> templateSetups = new LinkedList<>();

    public TemplateCommand() {
    }


    @Command("realm|realms template create")
    public void startTemplateSetup(PlayerSource playerSource) {
        if (getSetup(playerSource.source().getUniqueId()) != null) {
            playerSource.source().sendMessage("You are already setting up a template!");
            return;
        }
        templateSetups.add(new TemplateCreator(playerSource.source()));
    }

    @Command("realm|realms template create <arg>")
    public void templateSetupAction(PlayerSource playerSource, @Argument(suggestions = "creator") String arg) {
        playerSource.source().sendMessage("You selected action: " + arg);
    }

    @Command("realm|realms template edit <template>")
    public void templateEdit(PlayerSource playerSource, Template template) {

    }

    @Suggestions("creator")
    public List<String> templateSetupSuggestions(CommandContext<Source> context, CommandInput input) {
        if (!(context.sender() instanceof PlayerSource ps)) return List.of("invalid-sender");
        TemplateCreator creator = getSetup(ps.source().getUniqueId());
        if (creator != null) return creator.getSuggestion();
        return List.of("fallback");
    }

    public @Nullable TemplateCreator getSetup(UUID playerId) {
        for (TemplateCreator setup : templateSetups) {
            if (setup.getPlayerId().equals(playerId)) {
                return setup;
            }
        }
        return null;
    }

}
