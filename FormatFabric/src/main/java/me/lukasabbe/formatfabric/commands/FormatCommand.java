package me.lukasabbe.formatfabric.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.server.command.ServerCommandSource;

public interface FormatCommand {

    LiteralArgumentBuilder<FabricClientCommandSource> registerClientCommand();

    default int runClientCommand(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext, String args){
        return 0;
    }
    default int runClientCommand(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext){
        return 0;
    }

    LiteralArgumentBuilder<ServerCommandSource> registerServerCommand();

    default int runServerCommand(CommandContext<ServerCommandSource> ctx){
        return 0;
    }
    default int runServerCommand(CommandContext<ServerCommandSource> ctx, String args){
        return 0;
    }
}
