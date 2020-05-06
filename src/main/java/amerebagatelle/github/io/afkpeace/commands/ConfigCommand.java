package amerebagatelle.github.io.afkpeace.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.*;

import java.io.IOException;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.BoolArgumentType.bool;

public class ConfigCommand implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        LiteralArgumentBuilder<CottonClientCommandSource> afkpeace = literal("afkpeace")
            .then(literal("maxReconnectTries")  // * Configuration Options
                .executes(ctx -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("MaxReconnectTries is currently " + SettingsManager.maxReconnectTries));
                    return 1;
                }))
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
                    })))
            .then(literal("secondsBetweenReconnectionAttempts")
                .executes(ctx -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("SecondsBetweenReconnectionAttempts is currently " + SettingsManager.secondsBetweenReconnectionAttempts));
                    return 1;
                }))
            .then(literal("secondsBetweenReconnectionAttempts")
                .then(argument("seconds", integer())
                    .executes(ctx -> {
                        MinecraftClient mc = MinecraftClient.getInstance();
                        try {
                            String seconds = Integer.toString(IntegerArgumentType.getInteger(ctx, "seconds"));
                            SettingsManager.writeSetting("secondsBetweenReconnectionAttempts", seconds);
                            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("secondsBetweenReconnectionAttempts set to: " + seconds));
                            return 1;
                        } catch (IOException e) {
                            e.printStackTrace();
                            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("Was not able to set setting."));
                            return -1;
                        }
                    })))
            .then(literal("autoReconnect") // * Toggle Options
                .executes(ctx -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("AutoReconnect is currently " + SettingsManager.isReconnectOnTimeoutActive));
                    return 1;
                }))
            .then(literal("autoReconnect")
                .then(argument("setpoint", bool())
                    .executes(ctx -> {
                        MinecraftClient mc = MinecraftClient.getInstance();
                        boolean setpoint = BoolArgumentType.getBool(ctx, "setpoint");
                        SettingsManager.isReconnectOnTimeoutActive = setpoint;
                        mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("AutoReconnect set to: " + setpoint));
                        return 1;
                    })))
            .then(literal("damageLogout")
                .executes(ctx -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("DamageLogout is currently " + SettingsManager.isDamageProtectActive));
                    return 1;
                }))
            .then(literal("damageLogout")
                .then(argument("setpoint", bool())
                    .executes(ctx -> {
                        MinecraftClient mc = MinecraftClient.getInstance();
                        boolean setpoint = BoolArgumentType.getBool(ctx, "setpoint");
                        SettingsManager.isDamageProtectActive = setpoint;
                        mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("DamageLogout set to: " + setpoint));
                        return 1;
                    })));

        dispatcher.register(afkpeace);
    }

}