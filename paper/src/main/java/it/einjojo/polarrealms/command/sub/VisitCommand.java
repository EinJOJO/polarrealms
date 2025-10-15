package it.einjojo.polarrealms.command.sub;

import it.einjojo.polarrealms.world.RealmWorld;
import it.einjojo.polarrealms.world.executor.RealmVisitExecutor;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.paper.util.sender.PlayerSource;

@CommandContainer
public class VisitCommand {

    public VisitCommand() {
    }

    @Command("realm|realms visit <owner> [alias]")
    @CommandDescription("realms.cmd.visit.description")
    public void visitRealmByOwnerAndOptionalAlias(PlayerSource source, Player owner, String alias) {

    }

    @Command("realm|realms visit <realm>")
    @CommandDescription("realms.cmd.visit.description")
    public void visitRealmById(PlayerSource source, RealmWorld realm) {

    }

    public RealmVisitExecutor getVisitExecutor() {
        return null; //TODO
    }
}
