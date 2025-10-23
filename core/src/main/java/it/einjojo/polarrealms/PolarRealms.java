package it.einjojo.polarrealms;

import it.einjojo.polarrealms.event.dispatch.NetworkEventBus;
import it.einjojo.polarrealms.host.RealmHost;
import it.einjojo.polarrealms.player.OnlinePlayerHandleFactory;
import it.einjojo.polarrealms.player.RealmPlayer;
import it.einjojo.polarrealms.player.provider.NameProvider;
import it.einjojo.polarrealms.world.executor.RealmVisitExecutor;
import it.einjojo.polarrealms.world.loader.RealmLoader;
import it.einjojo.polarrealms.world.RealmStateManager;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface PolarRealms {

    RealmVisitExecutor getVisitExecutor();

    RealmLoader getLoader();

    OnlinePlayerHandleFactory getOnlinePlayerHandleFactory();

    RealmStateManager getRealmStateManager();

    Logger getLogger();

    Optional<RealmHost> getHostInformation();

    NetworkEventBus getEventBus();

    NameProvider getNameProvider();

    /**
     * Returns an instance of {@link RealmPlayer} for the given UUID.
     *
     * @param uuid the player's UUID
     * @return a {@link RealmPlayer} instance or {@link Optional#empty()} if the player could not be found.
     */
    Optional<RealmPlayer> getPlayer(UUID uuid);

}
