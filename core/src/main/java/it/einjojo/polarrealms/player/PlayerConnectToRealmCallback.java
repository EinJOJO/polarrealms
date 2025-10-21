package it.einjojo.polarrealms.player;

import com.google.common.eventbus.Subscribe;
import it.einjojo.polarrealms.event.dispatch.LettuceNetworkEventBus;
import it.einjojo.polarrealms.event.RealmPlayerEnterEvent;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Getter
public class PlayerConnectToRealmCallback {
    private final UUID uuid;
    private final CompletableFuture<UUID> future;
    private final LettuceNetworkEventBus eventBus;


    public PlayerConnectToRealmCallback(UUID uuid, LettuceNetworkEventBus eventBus, int timeoutSeconds) {
        this.uuid = uuid;
        this.eventBus = eventBus;
        eventBus.register(this);
        future = new CompletableFuture<UUID>().orTimeout(timeoutSeconds, TimeUnit.SECONDS);
    }

    @Subscribe
    public void onRealmEnter(RealmPlayerEnterEvent event) {
        if (future.isDone()) {
            eventBus.unregister(this);
            return;
        }
        if (event.getPlayerId().equals(uuid)) {
            fulfill(event.getRealmId());
        }
    }


    public void fulfill(UUID realmId) {
        future.complete(realmId);
        eventBus.unregister(this);
    }

    public void cancel() {
        future.cancel(true);
        eventBus.unregister(this);
    }
}
