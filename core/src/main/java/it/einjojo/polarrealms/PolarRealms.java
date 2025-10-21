package it.einjojo.polarrealms;

import it.einjojo.polarrealms.event.dispatch.LettuceNetworkEventBus;
import it.einjojo.polarrealms.host.RealmHost;
import it.einjojo.polarrealms.player.OnlinePlayerHandleFactory;
import it.einjojo.polarrealms.player.RealmPlayer;
import it.einjojo.polarrealms.world.executor.RealmVisitExecutor;
import it.einjojo.polarrealms.world.loader.RealmStateManager;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PolarRealms {

    CompletableFuture<Optional<RealmPlayer>> getPlayer(UUID playerId);

    RealmVisitExecutor getVisitExecutor();

    OnlinePlayerHandleFactory getOnlinePlayerHandleFactory();

    RealmStateManager getRealmStateManager();

    Logger getLogger();

    Optional<RealmHost> getHostInformation();

    LettuceNetworkEventBus getEventBus();

}
