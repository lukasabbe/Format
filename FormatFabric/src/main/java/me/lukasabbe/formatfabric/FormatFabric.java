package me.lukasabbe.formatfabric;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class FormatFabric implements ModInitializer {
    private static int x = 0;
    private static int y = 0;
    private static int z = 0;
    private static int dx = 0;
    private static int dy = 0;
    private static int dz = 0;

    private boolean hasUsed = false;

    @Override
    public void onInitialize() {
        initLeftClick();
        initRightClick();
        initTick();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(createFormatCommand());
            dispatcher.register(createFormatToolCommand());
        });
    }
    private LiteralArgumentBuilder<ServerCommandSource> createFormatToolCommand(){
        return CommandManager
                .literal("formattool")
                .requires(perm -> perm.hasPermissionLevel(1))
                .executes(ctx ->{
                    if(!ctx.getSource().isExecutedByPlayer()){
                        ctx.getSource().sendError(Text.of("Only players can execute this command"));
                        return 0;
                    }
                    ctx.getSource().getPlayer().giveItemStack(new ItemStack(Items.GOLDEN_AXE));
                    ctx.getSource().sendMessage(Text.of("You have now gotten format tool"));
                    return 1;
                });
    }

    private LiteralArgumentBuilder<ServerCommandSource> createFormatCommand(){
        return CommandManager
                .literal("format")
                .requires(perms->perms.hasPermissionLevel(1))
                .then(CommandManager
                        .literal("nbt")
                        .executes(ctx->{
                            if(!ctx.getSource().isExecutedByPlayer()){
                                ctx.getSource().sendError(Text.of("This command must be exectued by a player"));
                                return 0;
                            }
                            MutableText message = Text.literal(getStringNbt()).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, getStringNbt())));
                            ctx
                                    .getSource()
                                    .getPlayer()
                                    .sendMessage(message);
                            return 1;
                        })
                )
                .then(CommandManager
                        .literal("json")
                        .executes(ctx->{
                            if(!ctx.getSource().isExecutedByPlayer()){
                                ctx.getSource().sendError(Text.of("This command must be exectued by a player"));
                                return 0;
                            }
                            MutableText message = Text.literal(getStringJson()).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, getStringJson())));
                            ctx
                                    .getSource()
                                    .getPlayer()
                                    .sendMessage(message);
                            return 1;
                        }));
    }

    private void initRightClick(){
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if(hasUsed) return ActionResult.PASS;
            if(!player.isCreative()) return ActionResult.PASS;
            if(!player.getMainHandStack().isOf(Items.GOLDEN_AXE)) return ActionResult.PASS;
            if(world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.AIR)) return ActionResult.PASS;
            hasUsed = true;
            x = hitResult.getBlockPos().getX();
            y = hitResult.getBlockPos().getY();
            z = hitResult.getBlockPos().getZ();
            player.sendMessage(Text.of("This is the corner of were you want to create a area from"));
            return ActionResult.SUCCESS;
        });
    }
    private void initLeftClick(){
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if(!player.isCreative()) return ActionResult.PASS;
            if(!player.getMainHandStack().isOf(Items.GOLDEN_AXE)) return ActionResult.PASS;
            if(hasUsed) return ActionResult.SUCCESS;
            if(world.getBlockState(pos).isOf(Blocks.AIR)) return ActionResult.PASS;
            hasUsed = true;
            dx = pos.getX();
            dy = pos.getY();
            dz = pos.getZ();
            player.sendMessage(Text.of("This is the other corner."));
            player.stopUsingItem();
            return ActionResult.SUCCESS;
        });
    }

    private void initTick(){
        ServerTickEvents.START_SERVER_TICK.register(server -> hasUsed = false);
    }

    private String getStringNbt() {
        int tempDx = x - dx;
        int tempDy = y - dy;
        int tempDz = z - dz;
        return "[x=" + x + ",y=" + y + ",z=" + z + ",dx=" + tempDx * -1 + ",dy=" + tempDy * -1 + ",dz=" + tempDz * -1 + "]";
    }
    private String getStringJson() {
        int tempDx = x - dx;
        int tempDy = y - dy;
        int tempDz = z - dz;
        return "\"position\":{\"x\": {\"min\":"+Math.min(x, tempDx)+",\"max\":"+Math.max(x, tempDx)+"},\"y\":{\"min\":"+Math.min(y, tempDy)+",\"max\":"+Math.max(y, tempDy)+"},\"z\":{\"min\":"+Math.min(z, tempDz)+",\"max\":"+Math.max(z, tempDz)+"}}";
    }

}
