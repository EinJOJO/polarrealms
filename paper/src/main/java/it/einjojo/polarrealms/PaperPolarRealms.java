package it.einjojo.polarrealms;

import it.einjojo.polarrealms.command.CommandManager;
import it.einjojo.polarrealms.config.RealmsGlobalConfiguration;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;

public class PaperPolarRealms extends JavaPlugin {
    private MiniMessage miniMessage;
    private RealmsGlobalConfiguration config;

    @Getter
    private static PaperPolarRealms instance;

    @Override
    public void onLoad() {

        try {
            Files.createDirectory(getDataPath());
            config = RealmsGlobalConfiguration.load(getDataPath().resolve("config.json"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    @Override
    public void onEnable() {
        instance = this;

        new CommandManager(this);
    }

    @Override
    public void onDisable() {

    }

    public static MiniMessage miniMessage() {
        return instance.miniMessage;
    }
}
