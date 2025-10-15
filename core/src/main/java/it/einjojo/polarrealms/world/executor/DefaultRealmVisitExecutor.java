package it.einjojo.polarrealms.world.executor;


import it.einjojo.polarrealms.player.OnlinePlayerHandle;
import it.einjojo.polarrealms.world.RealmWorld;
import it.einjojo.polarrealms.world.loader.RealmLoader;

import java.util.concurrent.CompletableFuture;

public class DefaultRealmVisitExecutor implements RealmVisitExecutor {
    @Override
    public final CompletableFuture<Void> visit(RealmWorld realmWorld, OnlinePlayerHandle visitor) {
        return null;
    }

    @Override
    public final RealmLoader getRealmLoader() {
        return null;
    }
}
