package it.einjojo.polarrealms.command.sub;

import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.PolarRealmsPlugin;
import it.einjojo.polarrealms.exception.ComponentException;
import it.einjojo.polarrealms.player.RealmPlayer;
import it.einjojo.polarrealms.template.Template;
import it.einjojo.polarrealms.world.CreationContext;
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
    private final PolarRealms api;

    public CreateCommand() {
        this.api = PolarRealmsPlugin.getInstance().getApi();
    }

    @Command("realm|realms create")
    @CommandDescription("realms.cmd.create.description")
    public CompletableFuture<Void> createRealm(PlayerSource source, @Flag("template") Template template) {
        RealmPlayer rp = api.getPlayer(source.source().getUniqueId()).orElseThrow(() -> ComponentException.translatable("realm.generic-error"));
        if (rp.hasReachedRealmLimit()) {
            return CompletableFuture.failedFuture(ComponentException.translatable("realm.create.limit-reached"));
        }
        return api.getLoader().createRealm(CreationContext.builder()
                        .owner(source.source().getUniqueId())
                        .template(template)
                        .build())
                .thenAccept(realm -> {
                    realm.visit(api.getOnlinePlayerHandleFactory().createHandle(source.source()));
                });
    }


}
