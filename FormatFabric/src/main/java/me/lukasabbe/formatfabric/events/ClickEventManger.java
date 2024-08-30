package me.lukasabbe.formatfabric.events;

import me.lukasabbe.formatfabric.objects.BoxValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class ClickEventManger {
    private BoxValue boxValue;
    private HashMap<UUID, BoxValue> values;
    private boolean hasClickedClient = false;
    private HashMap<UUID, Boolean> hasClickedServer;
    private boolean hasAnyoneUsedFormat = false;
    private boolean isServerVersion = false;
    public ClickEventManger(BoxValue boxValue) {
        this.boxValue = boxValue;
    }
    public ClickEventManger(HashMap<UUID, BoxValue> values){
        isServerVersion = true;
        hasClickedServer = new HashMap<>();
        this.values = values;
    }

    public ActionResult rightClickEvent(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if(hasClickedClient) return ActionResult.PASS;
        if(!player.isCreative()) return ActionResult.PASS;
        if(!player.getMainHandStack().isOf(Items.GOLDEN_AXE)) return ActionResult.PASS;
        final BlockPos blockPos = hitResult.getBlockPos();
        if(world.getBlockState(blockPos).isOf(Blocks.AIR)) return ActionResult.PASS;
        setHasClicked(true,player);
        hasAnyoneUsedFormat = true;
        setX(blockPos.getX(),player);
        setY(blockPos.getY(),player);
        setZ(blockPos.getZ(),player);
        player.sendMessage(Text.of("This is the corner of were you want to create a area from"));
        return ActionResult.FAIL;
    }

    public ActionResult leftClickEvent(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        if(hasClickedClient) return ActionResult.PASS;
        if(!player.isCreative()) return ActionResult.PASS;
        if(!player.getMainHandStack().isOf(Items.GOLDEN_AXE)) return ActionResult.PASS;
        if(world.getBlockState(pos).isOf(Blocks.AIR)) return ActionResult.PASS;
        setHasClicked(true,player);
        hasAnyoneUsedFormat = true;
        setDx(pos.getX(),player);
        setDy(pos.getY(),player);
        setDz(pos.getZ(),player);
        player.sendMessage(Text.of("This is the other corner."));
        player.stopUsingItem();
        return ActionResult.FAIL;
    }

    public void tickEvent(MinecraftClient client) {
        setHasClicked(false, client.player);
        hasAnyoneUsedFormat = false;
    }

    public void tickEvent(MinecraftServer server) {
        if(hasAnyoneUsedFormat){
            hasClickedServer.forEach((player, value) -> hasClickedServer.put(player,false));
            hasAnyoneUsedFormat = false;
        }
    }

    private void setHasClicked(boolean value,PlayerEntity player){
        if(isServerVersion)
            hasClickedServer.put(player.getUuid(),value);
        else
            hasClickedClient = value;
    }

    private BoxValue getValue(PlayerEntity player){
        BoxValue temp = values.get(player.getUuid());
        if(temp == null)
            return new BoxValue();
        else
            return temp;
    }

    private void setX(int x, PlayerEntity player){
        if(isServerVersion){
            BoxValue temp = getValue(player);
            temp.setX(x);
            values.put(player.getUuid(),temp);
        }else{
            boxValue.setX(x);
        }
    }
    private void setY(int y, PlayerEntity player){
        if(isServerVersion){
            BoxValue temp = getValue(player);
            temp.setY(y);
            values.put(player.getUuid(),temp);
        }else{
            boxValue.setY(y);
        }
    }
    private void setZ(int z, PlayerEntity player){
        if(isServerVersion){
            BoxValue temp = getValue(player);
            temp.setZ(z);
            values.put(player.getUuid(),temp);
        }else{
            boxValue.setZ(z);
        }
    }
    private void setDx(int Dx, PlayerEntity player){
        if(isServerVersion){
            BoxValue temp = getValue(player);
            temp.setDx(Dx);
            values.put(player.getUuid(),temp);
        }else{
            boxValue.setDx(Dx);
        }
    }
    private void setDy(int Dy, PlayerEntity player){
        if(isServerVersion){
            BoxValue temp = getValue(player);
            temp.setDy(Dy);
            values.put(player.getUuid(),temp);
        }else{
            boxValue.setDy(Dy);
        }
    }
    private void setDz(int Dz, PlayerEntity player){
        if(isServerVersion){
            BoxValue temp = getValue(player);
            temp.setDz(Dz);
            values.put(player.getUuid(),temp);
        }else{
            boxValue.setDz(Dz);
        }
    }

    public boolean leftClickEventServer(World world, PlayerEntity player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        leftClickEvent(player,world,null,blockPos,null);
        return false;
    }
}
