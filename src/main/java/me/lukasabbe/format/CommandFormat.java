package me.lukasabbe.format;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandFormat implements CommandExecutor {
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

    private static String getStringNbt(Player p) {
        final int x = EventAxe.x;
        final int y = EventAxe.y;
        final int z = EventAxe.z;
        final int dx = x - EventAxe.dx;
        final int dy = y - EventAxe.dy;
        final int dz = z - EventAxe.dz;
        return "tellraw " + p.getName() + " {\"text\":\"[x=" + x + ",y=" + y + ",z=" + z + ",dx=" + dx * -1 + ",dy=" + dy * -1 + ",dz=" + dz * -1 + "]\",\"clickEvent\":{\"action\":\"copy_to_clipboard\",\"value\":\"[x=" + x + ",y=" + y + ",z=" + z + ",dx=" + dx * -1 + ",dy=" + dy * -1 + ",dz=" + dz * -1 + "]\"}}";
    }
    private static String getStringJson(Player p) {
        final int x = EventAxe.x;
        final int y = EventAxe.y;
        final int z = EventAxe.z;
        final int dx = x - EventAxe.dx;
        final int dy = y - EventAxe.dy;
        final int dz = z - EventAxe.dz;
        return "tellraw " + p.getName() + " [\"\",{\"text\":\"\\\"position\\\":{\\\"x\\\": {\\\"min\\\":"+Math.min(x, dx)+",\\\"max\\\":"+Math.max(x, dx)+"},\\\"y\\\":{\\\"min\\\":"+Math.min(y, dy)+",\\\"max\\\":"+Math.max(y, dy)+"},\\\"z\\\":{\\\"min\\\":"+Math.min(z, dz)+",\\\"max\\\":"+Math.max(z, dz)+"}}\",\"color\":\"white\",\"clickEvent\":{\"action\":\"copy_to_clipboard\",\"value\":\"\\\"position\\\":{\\\"x\\\": {\\\"min\\\":"+Math.min(x, dx)+",\\\"max\\\":"+Math.max(x, dx)+"},\\\"y\\\":{\\\"min\\\":"+Math.min(y, dy)+",\\\"max\\\":"+Math.max(y, dy)+"},\\\"z\\\":{\\\"min\\\":"+Math.min(z, dz)+",\\\"max\\\":"+Math.max(z, dz)+"}}\"}},{\"text\":\"\\n \"}]";
    }
}
