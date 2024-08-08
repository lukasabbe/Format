package me.lukasabbe.format;

import me.lukasabbe.format.commands.CommandFormat;
import me.lukasabbe.format.commands.CommandTool;
import me.lukasabbe.format.commands.TabCompleteCommandFormat;
import me.lukasabbe.format.events.EventAxe;
import me.lukasabbe.format.objects.BoxValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Format extends JavaPlugin {
    public HashMap<UUID, BoxValue> boxValues;
    @Override
    public void onEnable() {
        boxValues = new HashMap<>();
        this.getCommand("formattool").setExecutor(new CommandTool());
        this.getCommand("format").setExecutor(new CommandFormat(boxValues));
        this.getCommand("format").setTabCompleter(new TabCompleteCommandFormat());
        this.getServer().getPluginManager().registerEvents(new EventAxe(boxValues), this);
    }
}
