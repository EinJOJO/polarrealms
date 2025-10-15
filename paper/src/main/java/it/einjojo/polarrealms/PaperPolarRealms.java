package it.einjojo.polarrealms;

import it.einjojo.polarrealms.command.CommandManager;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperPolarRealms extends JavaPlugin {
    private MiniMessage miniMessage;


    @Getter
    private static PaperPolarRealms instance;

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
