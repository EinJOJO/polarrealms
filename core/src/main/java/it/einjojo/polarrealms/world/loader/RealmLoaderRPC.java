package it.einjojo.polarrealms.world.loader;

import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.world.ActiveRealmSnapshot;
import it.einjojo.polarrealms.world.CreationContext;
import it.einjojo.polarrealms.world.RealmWorld;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

/**
 * A remote procedure call implementation of {@link RealmLoader}.
 * <p>Given the core module is used on a service that is not a paper-host server, the api should be able to load a realm remotely.</p>
 * <p>The implementation picks one of the available hosts and tries to perform a remote procedure call.</p>
 */
public class RealmLoaderRPC implements RealmLoader {
    private final PolarRealms api;

    public RealmLoaderRPC(PolarRealms api) {
        this.api = api;
    }

    @Override
    public CompletableFuture<RealmWorld> createRealm(CreationContext context) {
        return null;
    }

    @Override
    public RealmStateManager getStateManager() {
        return api.getRealmStateManager();
    }

    @Override
    public CompletableFuture<@NonNull ActiveRealmSnapshot> withLoadedRealm(@NonNull RealmWorld realmWorld) {
        return null;
    }
}
