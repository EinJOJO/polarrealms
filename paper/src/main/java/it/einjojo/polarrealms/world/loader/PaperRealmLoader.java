package it.einjojo.polarrealms.world.loader;

import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.repository.RealmRepository;
import it.einjojo.polarrealms.storage.WorldFileStorage;
import it.einjojo.polarrealms.template.Template;
import it.einjojo.polarrealms.world.RealmHandle;
import it.einjojo.polarrealms.world.RealmProperties;
import it.einjojo.polarrealms.world.RealmWorld;
import it.einjojo.polarrealms.world.creation.CreationContext;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * A paper realm loader holds all {@link RealmHandle} instances which are loaded by the plugin.
 */
@NullMarked
public class PaperRealmLoader implements RealmLoader {
    private final List<RealmHandle> loadedRealms = new LinkedList<>();
    private final PolarRealms api;
    private final RealmRepository realmRepository;
    private final WorldFileStorage worldFileStorage;

    public PaperRealmLoader(PolarRealms api, RealmRepository realmRepository, WorldFileStorage worldFileStorage) {
        this.api = api;
        this.realmRepository = realmRepository;
        this.worldFileStorage = worldFileStorage;
    }

    @Override
    public CompletableFuture<RealmWorld> createRealm(CreationContext context) {
        return CompletableFuture.supplyAsync(() -> createRealmSync(context));
    }

    private RealmWorld createRealmSync(CreationContext context) {
        if (realmRepository.existsById(context.getRealmId())) {
            throw new IllegalArgumentException("Realm with id " + context.getRealmId() + " already exists");
        }
        api.getLogger().info("persisting realm... {}", context);
        Template realmTemplate = context.getTemplate();
        RealmProperties properties = realmTemplate.createProperties(context);
        RealmWorld newRealm = new RealmWorld(context.getRealmId(), context.getOwner(), properties, api);
        realmRepository.saveRealm(newRealm);
        try {
            worldFileStorage.createNewWorldFile(context.getTemplate());
        } catch (IOException e) {
            throw new RuntimeException("Could not copy template", e);
        }

    }

    public Optional<RealmHandle> getRealmHandle(UUID realmId) {
        for (RealmHandle realmHandle : loadedRealms) {
            if (realmHandle.getRealmId().equals(realmId)) {
                return Optional.of(realmHandle);
            }
        }
        return Optional.empty();
    }

    @Override
    public RealmStateManager getStateManager() {
        return api.getRealmStateManager();
    }
}
