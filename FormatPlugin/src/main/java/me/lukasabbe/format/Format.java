package me.lukasabbe.format;

import org.bukkit.plugin.java.JavaPlugin;

public final class Format extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("formattool").setExecutor(new CommandTool());
        this.getCommand("format").setExecutor(new CommandFormat());
        this.getCommand("format").setTabCompleter(new TabCompleteCommandFormat());
        this.getServer().getPluginManager().registerEvents(new EventAxe(), this);
    }
}
