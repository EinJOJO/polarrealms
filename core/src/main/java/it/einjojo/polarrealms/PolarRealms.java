package it.einjojo.polarrealms;

import it.einjojo.polarrealms.event.dispatch.NetworkEventBus;
import it.einjojo.polarrealms.host.RealmHost;
import it.einjojo.polarrealms.player.OnlinePlayerHandleFactory;
import it.einjojo.polarrealms.world.executor.RealmVisitExecutor;
import it.einjojo.polarrealms.world.loader.RealmStateManager;
import org.slf4j.Logger;

import java.util.Optional;

public interface PolarRealms {

    RealmVisitExecutor getVisitExecutor();

    OnlinePlayerHandleFactory getOnlinePlayerHandleFactory();

    RealmStateManager getRealmStateManager();

    Logger getLogger();

    Optional<RealmHost> getHostInformation();

    NetworkEventBus getEventBus();

}
