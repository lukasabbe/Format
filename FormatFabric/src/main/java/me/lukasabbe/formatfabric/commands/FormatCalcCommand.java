package me.lukasabbe.formatfabric.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.lukasabbe.formatfabric.objects.BoxValue;
import me.lukasabbe.formatfabric.util.FormatCalc;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.UUID;

public class FormatCalcCommand implements FormatCommand{

    private BoxValue boxValue;
    private HashMap<UUID,BoxValue> boxValues;
    public FormatCalcCommand(BoxValue boxValue) {
        this.boxValue = boxValue;
    }
    public FormatCalcCommand(HashMap<UUID,BoxValue> boxValues) {
        this.boxValues = boxValues;
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> registerClientCommand() {
        return ClientCommandManager
                .literal("format")
                .then(ClientCommandManager
                        .literal("nbt")
                        .executes(ctx -> runClientCommand(ctx,"nbt")))
                .then(ClientCommandManager
                        .literal("json")
                        .executes(ctx -> runClientCommand(ctx,"json")));
    }


    public int runClientCommand(CommandContext<FabricClientCommandSource> ctx, String arg) {
        if(arg.equals("nbt")){
            ClientPlayerEntity player = ctx.getSource().getPlayer();
            if(!player.isCreative()){
                ctx.getSource().sendError(Text.literal("You need to be in creative to run this command"));
                return 0;
            }
            final String stringNbt = FormatCalc.getStringNbt(boxValue);
            final ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, stringNbt);
            MutableText message = Text
                    .literal(stringNbt)
                    .setStyle(Style.EMPTY.withClickEvent(clickEvent));
            ctx.getSource().getPlayer().sendMessage(message);
            return 1;
        }
        else{
            ClientPlayerEntity player = ctx.getSource().getPlayer();
            if(!player.isCreative()){
                ctx.getSource().sendError(Text.literal("You need to be in creative to run this command"));
                return 0;
            }
            final String stringJson = FormatCalc.getStringJson(boxValue);
            final ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, stringJson);
            MutableText message = Text
                    .literal(stringJson)
                    .setStyle(Style.EMPTY.withClickEvent(clickEvent));
            ctx.getSource().getPlayer().sendMessage(message);
            return 1;
        }
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerServerCommand() {
        return CommandManager
                .literal("format")
                .then(CommandManager
                        .literal("nbt")
                        .executes(ctx -> runServerCommand(ctx,"nbt")))
                .then(CommandManager
                        .literal("json")
                        .executes(ctx -> runServerCommand(ctx,"json")));
    }

    @Override
    public int runServerCommand(CommandContext<ServerCommandSource> ctx, String args) {
        final ServerCommandSource source = ctx.getSource();
        if(!source.isExecutedByPlayer()){
            source.sendError(Text.literal("This command must med executed by a player"));
            return 0;
        }
        if(args.equals("nbt")){
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            if(!player.isCreative()){
                ctx.getSource().sendError(Text.literal("You need to be in creative to run this command"));
                return 0;
            }
            final String stringNbt = FormatCalc.getStringNbt(boxValues.get(player.getUuid()));
            final ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, stringNbt);
            MutableText message = Text
                    .literal(stringNbt)
                    .setStyle(Style.EMPTY.withClickEvent(clickEvent));
            ctx.getSource().getPlayer().sendMessage(message);
            return 1;
        }
        else{
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            if(!player.isCreative()){
                ctx.getSource().sendError(Text.literal("You need to be in creative to run this command"));
                return 0;
            }
            final String stringJson = FormatCalc.getStringJson(boxValues.get(player.getUuid()));
            final ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, stringJson);
            MutableText message = Text
                    .literal(stringJson)
                    .setStyle(Style.EMPTY.withClickEvent(clickEvent));
            ctx.getSource().getPlayer().sendMessage(message);
            return 1;
        }
    }
}
