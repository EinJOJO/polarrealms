package it.einjojo.polarrealms;

import it.einjojo.polarrealms.command.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperPolarRealms extends JavaPlugin implements PolarRealms {

    @Override
    public void onEnable() {
        new CommandManager(this).registerCommands();
    }

    @Override
    public void onDisable() {

    }
}
