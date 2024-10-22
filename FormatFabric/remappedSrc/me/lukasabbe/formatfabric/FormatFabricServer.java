package me.lukasabbe.formatfabric;

import me.lukasabbe.formatfabric.commands.FormatCalcCommand;
import me.lukasabbe.formatfabric.commands.FormatCommand;
import me.lukasabbe.formatfabric.commands.FormatToolCommand;
import me.lukasabbe.formatfabric.events.ClickEventManger;
import me.lukasabbe.formatfabric.objects.BoxValue;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FormatFabricServer implements DedicatedServerModInitializer {
    private HashMap<UUID, BoxValue> boxValues;
    @Override
    public void onInitializeServer() {
        boxValues = new HashMap<>();
        registerEvents();
        registerCommands();
    }
    private void registerEvents(){
        ClickEventManger manger = new ClickEventManger(boxValues);
        UseBlockCallback.EVENT.register(manger::rightClickEvent);
        PlayerBlockBreakEvents.BEFORE.register(manger::leftClickEventServer);
    }
    private void registerCommands(){
        List<FormatCommand> commands = Arrays.asList(new FormatToolCommand(), new FormatCalcCommand(boxValues));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess,registrationEnvironment) ->
                commands.forEach(formatCommand -> dispatcher.register(formatCommand.registerServerCommand())));
    }
}
