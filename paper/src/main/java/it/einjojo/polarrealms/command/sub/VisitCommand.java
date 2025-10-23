package it.einjojo.polarrealms.command.sub;

import it.einjojo.polarrealms.PolarRealmsPlugin;
import it.einjojo.polarrealms.world.RealmWorld;
import it.einjojo.polarrealms.world.executor.RealmVisitExecutor;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.paper.util.sender.PlayerSource;

@CommandContainer
public class VisitCommand {

    public VisitCommand() {
    }

    @Command("realm|realms visit <realm>")
    @CommandDescription("realms.cmd.visit.description")
    public void visitRealmByOwnerAndOptionalAlias(PlayerSource source, RealmWorld realm) {
        source.source().sendMessage("executed!");
    }


    public RealmVisitExecutor getVisitExecutor() {
        return PolarRealmsPlugin.getInstance().getApi().getVisitExecutor();
    }
}
