package it.einjojo.polarrealms;

import it.einjojo.polarrealms.player.OnlinePlayerHandleFactory;
import it.einjojo.polarrealms.player.RealmPlayer;
import it.einjojo.polarrealms.world.executor.RealmVisitExecutor;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PolarRealms {

    CompletableFuture<Optional<RealmPlayer>> getPlayer(UUID playerId);

    RealmVisitExecutor getVisitExecutor();

    OnlinePlayerHandleFactory getOnlinePlayerHandleFactory();

}
