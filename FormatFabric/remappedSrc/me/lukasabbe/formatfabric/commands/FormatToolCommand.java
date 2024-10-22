package me.lukasabbe.formatfabric.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FormatToolCommand implements FormatCommand{
    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> registerClientCommand() {
        return ClientCommandManager
                .literal("formattool")
                .executes(this::runClientCommand);
    }

    @Override
    public int runClientCommand(CommandContext<FabricClientCommandSource> ctx) {
        final FabricClientCommandSource source = ctx.getSource();
        final ClientPlayerEntity player = source.getPlayer();
        if(!player.isCreative()){
            source.sendError(Text.literal("You need to be in creative to run this command"));
            return 0;
        }
        player.giveItemStack(new ItemStack(Items.GOLDEN_AXE));
        source.sendFeedback(Text.literal("Gave you format tool"));
        return 1;
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerServerCommand() {
        return CommandManager
                .literal("formattool")
                .requires(p -> p.hasPermissionLevel(2))
                .executes(this::runServerCommand);
    }

    @Override
    public int runServerCommand(CommandContext<ServerCommandSource> ctx) {
        final ServerCommandSource source = ctx.getSource();
        if(!source.isExecutedByPlayer()){
            source.sendError(Text.literal("This command must med executed by a player"));
            return 0;
        }
        final ServerPlayerEntity player = source.getPlayer();
        if(!player.isCreative()){
            source.sendError(Text.literal("You need to be in creative to run this command"));
            return 0;
        }
        player.giveItemStack(new ItemStack(Items.GOLDEN_AXE));
        player.sendMessage(Text.literal("Gave you format tool"));
        return 1;
    }
}
