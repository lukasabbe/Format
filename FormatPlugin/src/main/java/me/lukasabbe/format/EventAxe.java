package me.lukasabbe.format;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EventAxe implements Listener {
    public static int x;
    public static int y;
    public static int z;
    public static int dx;
    public static int dy;
    public static int dz = 0;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack tool = new ItemStack(Material.GOLDEN_AXE);
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) return;
        if(e.getItem() == null) return;
        if(e.getHand() == null) return;
        if(e.getClickedBlock() == null) return;
        if(e.getClickedBlock().getBlockData().getMaterial() == Material.AIR) return;

        if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getGameMode() == GameMode.CREATIVE && e.getItem().isSimilar(tool)) {
            x = e.getClickedBlock().getX();
            y = e.getClickedBlock().getY();
            z = e.getClickedBlock().getZ();
            e.getPlayer().sendMessage("This is the corner of were you want to create a area from");
        }

        if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.LEFT_CLICK_BLOCK && e.getPlayer().getGameMode() == GameMode.CREATIVE && e.getItem().isSimilar(tool)) {
            e.setCancelled(true);
            dx = e.getClickedBlock().getX();
            dy = e.getClickedBlock().getY();
            dz = e.getClickedBlock().getZ();
            e.getPlayer().sendMessage("This is the other corner.");
        }

    }
}
