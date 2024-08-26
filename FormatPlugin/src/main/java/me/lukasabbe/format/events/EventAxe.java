package me.lukasabbe.format.events;

import me.lukasabbe.format.objects.BoxValue;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class EventAxe implements Listener {

    public HashMap<UUID, BoxValue> boxValues;

    public EventAxe(HashMap<UUID, BoxValue> boxValues) {
        this.boxValues = boxValues;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack tool = new ItemStack(Material.GOLDEN_AXE);
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) return;
        if(e.getItem() == null) return;
        if(e.getHand() == null) return;
        final Block clickedBlock = e.getClickedBlock();
        if(clickedBlock == null) return;
        if(clickedBlock.getBlockData().getMaterial() == Material.AIR) return;
        final UUID uniqueId = e.getPlayer().getUniqueId();
        if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getGameMode() == GameMode.CREATIVE && e.getItem().isSimilar(tool)) {
            setBoxX(clickedBlock.getX(), uniqueId);
            setBoxY(clickedBlock.getY(), uniqueId);
            setBoxZ(clickedBlock.getZ(), uniqueId);
            e.getPlayer().sendMessage("This is the corner of were you want to create a area from");
        }

        if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.LEFT_CLICK_BLOCK && e.getPlayer().getGameMode() == GameMode.CREATIVE && e.getItem().isSimilar(tool)) {
            e.setCancelled(true);
            setBoxDx(clickedBlock.getX(), uniqueId);
            setBoxDy(clickedBlock.getY(), uniqueId);
            setBoxDz(clickedBlock.getZ(), uniqueId);
            e.getPlayer().sendMessage("This is the other corner.");
        }
    }
    public void setBoxX(int x,UUID uuid){
        if(boxValues.get(uuid) != null){
            BoxValue temp = boxValues.get(uuid);
            temp.setX(x);
            boxValues.put(uuid,temp);
        }else{
            BoxValue boxValue = new BoxValue();
            boxValue.setX(x);
            boxValues.put(uuid,boxValue);
        }
    }
    public void setBoxY(int y,UUID uuid){
        if(boxValues.get(uuid) != null){
            BoxValue temp = boxValues.get(uuid);
            temp.setY(y);
            boxValues.put(uuid,temp);
        }else{
            BoxValue boxValue = new BoxValue();
            boxValue.setY(y);
            boxValues.put(uuid,boxValue);
        }
    }
    public void setBoxZ(int z,UUID uuid){
        if(boxValues.get(uuid) != null){
            BoxValue temp = boxValues.get(uuid);
            temp.setZ(z);
            boxValues.put(uuid,temp);
        }else{
            BoxValue boxValue = new BoxValue();
            boxValue.setZ(z);
            boxValues.put(uuid,boxValue);
        }
    }
    public void setBoxDx(int dx,UUID uuid){
        if(boxValues.get(uuid) != null){
            BoxValue temp = boxValues.get(uuid);
            temp.setDx(dx);
            boxValues.put(uuid,temp);
        }else{
            BoxValue boxValue = new BoxValue();
            boxValue.setDx(dx);
            boxValues.put(uuid,boxValue);
        }
    }
    public void setBoxDy(int dy,UUID uuid){
        if(boxValues.get(uuid) != null){
            BoxValue temp = boxValues.get(uuid);
            temp.setDy(dy);
            boxValues.put(uuid,temp);
        }else{
            BoxValue boxValue = new BoxValue();
            boxValue.setDy(dy);
            boxValues.put(uuid,boxValue);
        }
    }
    public void setBoxDz(int dz,UUID uuid){
        if(boxValues.get(uuid) != null){
            BoxValue temp = boxValues.get(uuid);
            temp.setDz(dz);
            boxValues.put(uuid,temp);
        }else{
            BoxValue boxValue = new BoxValue();
            boxValue.setDz(dz);
            boxValues.put(uuid,boxValue);
        }
    }
}
