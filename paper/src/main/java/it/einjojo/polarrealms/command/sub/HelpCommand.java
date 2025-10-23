package it.einjojo.polarrealms.command.sub;

import it.einjojo.polarrealms.PolarRealmsPlugin;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.paper.util.sender.Source;

@CommandContainer
public class HelpCommand {


    @Command("realm|realms help")
    public void basicHelp(Source source) {
        source.source().sendMessage(PolarRealmsPlugin.miniMessage().deserialize("""
                <prefix> ʜɪʟꜰᴇ
                <prefix> /realm Dein Realm besuchen.
                """));
    }

}
