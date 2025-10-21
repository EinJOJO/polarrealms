package it.einjojo.polarrealms.template;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.util.TriState;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 */
@Slf4j
class _1WorldSelection extends Stage {

    @Override
    public List<String> suggestions(Player player) {
        try (Stream<Path> stream = Files.list(Bukkit.getWorldContainer().toPath())) {
            return stream
                    .filter(Files::isDirectory)
                    .filter(path -> Files.exists(path.resolve("level.dat")))
                    .map(path -> path.getFileName().toString()).toList();
        } catch (IOException e) {
            log.error("Failed to list worlds", e);
            return List.of();
        }
    }

    @Getter
    private World world;

    @Override
    public boolean isDone() {
        return world != null;
    }

    @Override
    public boolean handleInput(Player player, String input) {
        World alreadyLoaded = Bukkit.getWorld(input);
        if (alreadyLoaded != null) {
            this.world = alreadyLoaded;
            return true;
        }
        world = WorldCreator.name(input)
                .keepSpawnLoaded(TriState.FALSE)
                .environment(World.Environment.NORMAL)
                .generateStructures(false).createWorld();
        if (world == null) {
            player.sendMessage(miniMessage().deserialize("<creator> Welt konnte nicht erstellt werden!"));
            return false;
        }
        player.teleport(world.getSpawnLocation());

        return true;
    }

    @Override
    public void printInstructions(Player player) {
        miniMessage().deserialize("<creator> Welche Welt soll als Realm Template genutzt werden?");
        miniMessage().deserialize("<creator> <cmd> [...]");
    }


}
