package it.einjojo.polarrealms.command.sub;

import it.einjojo.polarrealms.player.OnlinePlayerHandleFactory;
import it.einjojo.polarrealms.template.Template;
import it.einjojo.polarrealms.world.CreationContext;
import it.einjojo.polarrealms.world.loader.RealmLoader;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

@CommandContainer
@NullMarked
public class CreateCommand {

    public CreateCommand() {
    }

    @Command("realm|realms create")
    @CommandDescription("realms.cmd.create.description")
    public CompletableFuture<Void> createRealm(PlayerSource source, @Flag("template") Template template) {
        //TODO max. realms reached check
        return getLoader().createRealm(CreationContext.builder()
                        .owner(source.source().getUniqueId())
                        .template(template)
                        .build())
                .thenAccept(realm -> {
                    realm.visit(getHandleFactory().createHandle(source.source()));
                });
    }


    public RealmLoader getLoader() {
        return null; // TODO
    }

    public OnlinePlayerHandleFactory getHandleFactory() {
        return null; // TODO
    }

}
