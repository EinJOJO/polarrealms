package it.einjojo.polarrealms;

import it.einjojo.polarrealms.command.CommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperPolarRealms extends JavaPlugin {


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
}
