package it.einjojo.polarrealms.template;

import io.papermc.paper.math.BlockPosition;
import io.papermc.paper.math.Position;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
class _2SpawnSelection extends Stage {
    private Location spawnLocation = null;

    @Override
    boolean isDone() {
        return spawnLocation != null;
    }

    @Override
    List<String> suggestions(Player player) {
        return List.of();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    boolean handleInput(Player player, String input) {
        spawnLocation = player.getLocation();
        Map<BlockPosition, BlockData> blocks = new HashMap<>();
        int y = spawnLocation.getBlockY();
        for (int i = 0; i < 15; i++) {
            BlockData greenGlass = Material.LIME_STAINED_GLASS.createBlockData();
            blocks.put(Position.block(spawnLocation.getBlockX(), y - i - 1, spawnLocation.getBlockZ()), greenGlass);
        }
        BlockPosition fakeBeacon = spawnLocation.clone().add(0, -16, 0).toBlock();
        int cx = fakeBeacon.blockX();
        int cy = fakeBeacon.blockY() - 1;
        int cz = fakeBeacon.blockZ();
        blocks.put(fakeBeacon, Material.BEACON.createBlockData());
        BlockData diamondBlock = Material.DIAMOND_BLOCK.createBlockData();
        for (int x = cx - 1; x <= cx + 1; x++) {
            for (int z = cz - 1; z <= cz + 1; z++) {
                blocks.put(Position.block(x, cy, z), diamondBlock);
            }
        }
        player.sendMultiBlockChange(blocks);
        if (getPreviousStage() instanceof _1WorldSelection ws) {
            ws.getWorld().setSpawnLocation(spawnLocation);
            ws.getWorld().getWorldBorder().setCenter(spawnLocation);
        }

        player.sendMessage(miniMessage.deserialize("<creator> Der Spawnpunkt wurde gesetzt."));
        return true;
    }

    @Override
    void printInstructions(Player player) {
        player.sendMessage(miniMessage.deserialize("<creator> Begebe dich zum Spawnpunkt"));
        player.sendMessage(miniMessage.deserialize("<creator> Führe /realm setup aus, um den Spawn zu setzen."));
        player.sendMessage(miniMessage.deserialize("<creator> <button>", Placeholder.component("button", Component.text("[Spawn platzieren]", NamedTextColor.GREEN).decorate(TextDecoration.BOLD).clickEvent(ClickEvent.runCommand("/realm setup")))));
    }
}
