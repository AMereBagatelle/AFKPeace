package net.bagatelle.afkpeace.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.bagatelle.afkpeace.settings.SettingsManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.*;

import java.io.IOException;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

public class ConfigCommand implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        LiteralArgumentBuilder<CottonClientCommandSource> afkpeace = literal("afkpeace")
            .then(literal("maxReconnectTries")
                .then(argument("tries", integer())
                    .executes(ctx -> {
                        MinecraftClient mc = MinecraftClient.getInstance();
                        try {
                            String tries = Integer.toString(IntegerArgumentType.getInteger(ctx, "tries"));
                            SettingsManager.writeSetting("maxReconnectTries", tries);
                            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("MaxReconnectTries set to: " + tries));
                            return 1;
                        } catch (IOException e) {
                            e.printStackTrace();
                            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("Was not able to set setting."));
                            return -1;
                        }
                    })
                ))
            .executes(ctx -> {
                return 1;
            });

        dispatcher.register(afkpeace);
    }

}