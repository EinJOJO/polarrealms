package it.einjojo.polarrealms.world.loader;

import it.einjojo.polarrealms.PolarRealms;
import it.einjojo.polarrealms.host.RealmHost;
import it.einjojo.polarrealms.repository.RealmRepository;
import it.einjojo.polarrealms.storage.WorldFileStorage;
import it.einjojo.polarrealms.template.Template;
import it.einjojo.polarrealms.world.*;
import live.minehub.polarpaper.Polar;
import live.minehub.polarpaper.PolarReader;
import live.minehub.polarpaper.PolarWorld;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * A paper realm loader holds all {@link RealmHandle} instances which are loaded by the plugin.
 * Creates realms
 */
@NullMarked
public class PaperRealmLoader implements RealmLoader {

    private final List<RealmHandle> loadedRealms = new LinkedList<>();
    private final PolarRealms api;
    private final RealmRepository realmRepository;
    private final WorldFileStorage worldFileStorage;
    private final RealmHost host;

    public PolarConfigFactory polarConfigFactory = PolarConfigFactory.DEFAULT;

    public PaperRealmLoader(PolarRealms api, RealmRepository realmRepository, WorldFileStorage worldFileStorage, RealmHost host) {
        this.api = api;
        this.realmRepository = realmRepository;
        this.worldFileStorage = worldFileStorage;
        this.host = host;
    }

    @Override
    public CompletableFuture<RealmWorld> createRealm(CreationContext context) {
        return CompletableFuture.supplyAsync(() -> createRealmSync(context).getRealmWorld());
    }

    private RealmHandle createRealmSync(CreationContext context) {
        if (realmRepository.existsById(context.getRealmId())) {
            throw new IllegalArgumentException("Realm with id " + context.getRealmId() + " already exists");
        }
        api.getLogger().info("Persisting realm... {}", context);
        Template realmTemplate = context.getTemplate();
        RealmProperties properties = realmTemplate.createProperties(context);
        RealmWorld newRealm = new RealmWorld(
                context.getRealmId(),
                context.getOwner(),
                context.isRandomName() ?
                        context.getRealmName() + "TODO" :
                        context.getRealmName(),
                -1,
                System.currentTimeMillis(),
                properties,
                api);

        realmRepository.saveRealm(newRealm);
        api.getLogger().info("Realm persisted in database.");
        try {
            api.getLogger().info("Copying template...");
            worldFileStorage.createNewWorldFile(newRealm.getRealmId(), context.getTemplate());
            api.getLogger().info("Copying template inside storage completed.");
        } catch (IOException e) {
            throw new RuntimeException("Could not copy template", e);
        }
        try {
            return loadRealm(newRealm);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RealmHandle loadRealm(RealmWorld realmWorld) throws IOException {
        api.getLogger().info("Loading realm...");
        getStateManager().claimLock(realmWorld.getRealmId(), host);
        api.getLogger().info("1/4 Claimed lock.");
        byte[] polarWorldBytes = worldFileStorage.loadWorld(realmWorld.getRealmId());
        api.getLogger().info("2/4 Loaded from storage. ({} bytes)", polarWorldBytes.length);
        PolarWorld polarWorld = PolarReader.read(polarWorldBytes);
        Polar.loadWorld(polarWorld, realmWorld.getRealmId().toString(), polarConfigFactory.createConfig(realmWorld));
        api.getLogger().info("3/4 Loaded by Polar.");
        RealmHandle handle = new RealmHandle(realmWorld, polarWorld, this, host.getInternalName());
        loadedRealms.add(handle);
        api.getLogger().info("4/4 Created Handle instance. Realm loaded successfully. ({} realms loaded.)", loadedRealms.size());
        return handle;
    }

    @Override
    public CompletableFuture<ActiveRealmSnapshot> withLoadedRealm(RealmWorld realmWorld) {
        return null; //TODO Create instances of active snapshots
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
