package me.lukasabbe.format;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandTool implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            ItemStack Tool = new ItemStack(Material.GOLDEN_AXE);
            p.getInventory().addItem(Tool);
            p.sendMessage(ChatColor.GREEN + "Added formatting tool");
        }

        return true;
    }
}
