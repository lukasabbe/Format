package me.lukasabbe.formatfabric;

import me.lukasabbe.formatfabric.commands.FormatCalcCommand;
import me.lukasabbe.formatfabric.commands.FormatCommand;
import me.lukasabbe.formatfabric.commands.FormatToolCommand;
import me.lukasabbe.formatfabric.events.ClickEventManger;
import me.lukasabbe.formatfabric.objects.BoxValue;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import java.util.Arrays;
import java.util.List;

public class FormatFabricClient implements ClientModInitializer {
    private BoxValue boxValue;
    @Override
    public void onInitializeClient() {
        boxValue = new BoxValue();
        registerEvents();
        registerCommands();
    }
    private void registerEvents(){
        ClickEventManger manger = new ClickEventManger(boxValue);
        UseBlockCallback.EVENT.register(manger::rightClickEvent);
        AttackBlockCallback.EVENT.register(manger::leftClickEvent);
        ClientTickEvents.END_CLIENT_TICK.register(manger::tickEvent);
    }
    private void registerCommands(){
        List<FormatCommand> commands = Arrays.asList(new FormatToolCommand(), new FormatCalcCommand(boxValue));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                commands.forEach(formatCommand -> dispatcher.register(formatCommand.registerClientCommand())));
    }
}
