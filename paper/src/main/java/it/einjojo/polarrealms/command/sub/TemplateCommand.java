package it.einjojo.polarrealms.command.sub;

import it.einjojo.polarrealms.template.TemplateCreator;
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


    @Command("realm setup [arg]")
    public void templateSetupAction(PlayerSource playerSource, @Nullable @Argument(value = "arg", suggestions = "creator") String arg) {
        var setup = getSetup(playerSource.source().getUniqueId());
        if (setup == null) {
            setup = new TemplateCreator(playerSource.source());
            templateSetups.add(setup);
        }
        setup.next(arg);
    }


    @Suggestions("creator")
    public List<String> templateSetupSuggestions(CommandContext<Source> context, CommandInput input) {
        if (!(context.sender() instanceof PlayerSource ps)) return List.of();
        TemplateCreator creator = getSetup(ps.source().getUniqueId());
        if (creator != null) return creator.getSuggestion();
        return List.of();
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
