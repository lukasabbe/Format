package me.lukasabbe.format.commands;

import me.lukasabbe.format.events.EventAxe;
import me.lukasabbe.format.objects.BoxValue;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandFormat implements CommandExecutor {
    public HashMap<UUID, BoxValue> boxValues;

    public CommandFormat(HashMap<UUID, BoxValue> boxValues) {
        this.boxValues = boxValues;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String cmd = "";
            if(strings.length == 0)
                return false;
            else if(strings[0].equals("json"))
                cmd = getStringJson(p);
            else if(strings[0].equals("nbt"))
                cmd = getStringNbt(p);
            else
                return false;
            Bukkit.dispatchCommand(console, cmd);
        }

        return true;
    }

    private String getStringNbt(Player p) {
        final BoxValue boxValue = boxValues.get(p.getUniqueId());
        final int x = boxValue.getX();
        final int y = boxValue.getY();
        final int z = boxValue.getZ();
        final int dx = x - boxValue.getDx();
        final int dy = y - boxValue.getDy();
        final int dz = z - boxValue.getDz();
        return "tellraw " + p.getName() + " {\"text\":\"[x=" + x + ",y=" + y + ",z=" + z + ",dx=" + dx * -1 + ",dy=" + dy * -1 + ",dz=" + dz * -1 + "]\",\"clickEvent\":{\"action\":\"copy_to_clipboard\",\"value\":\"[x=" + x + ",y=" + y + ",z=" + z + ",dx=" + dx * -1 + ",dy=" + dy * -1 + ",dz=" + dz * -1 + "]\"}}";
    }
    private String getStringJson(Player p) {
        final BoxValue boxValue = boxValues.get(p.getUniqueId());
        final int x = boxValue.getX();
        final int y = boxValue.getY();
        final int z = boxValue.getZ();
        final int dx = x - boxValue.getDx();
        final int dy = y - boxValue.getDy();
        final int dz = z - boxValue.getDz();
        return "tellraw " + p.getName() + " [\"\",{\"text\":\"\\\"position\\\":{\\\"x\\\": {\\\"min\\\":"+Math.min(x, dx)+",\\\"max\\\":"+Math.max(x, dx)+"},\\\"y\\\":{\\\"min\\\":"+Math.min(y, dy)+",\\\"max\\\":"+Math.max(y, dy)+"},\\\"z\\\":{\\\"min\\\":"+Math.min(z, dz)+",\\\"max\\\":"+Math.max(z, dz)+"}}\",\"color\":\"white\",\"clickEvent\":{\"action\":\"copy_to_clipboard\",\"value\":\"\\\"position\\\":{\\\"x\\\": {\\\"min\\\":"+Math.min(x, dx)+",\\\"max\\\":"+Math.max(x, dx)+"},\\\"y\\\":{\\\"min\\\":"+Math.min(y, dy)+",\\\"max\\\":"+Math.max(y, dy)+"},\\\"z\\\":{\\\"min\\\":"+Math.min(z, dz)+",\\\"max\\\":"+Math.max(z, dz)+"}}\"}},{\"text\":\"\\n \"}]";
    }
}
