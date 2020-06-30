package amerebagatelle.github.io.afkpeace.commands;

import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.argument;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;

@Environment(EnvType.CLIENT)
public class ConfigCommand implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        LiteralArgumentBuilder<CottonClientCommandSource> afkpeace = literal("afkpeace")
                .then(literal("reconnectEnabled")
                        .executes(ctx -> {
                            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("reconnectEnabled is set to" + SettingsManager.loadSetting("reconnectEnabled")), MinecraftClient.getInstance().player.getUuid());
                            return 1;
                        })
                        .then(argument("setpoint", bool())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("reconnectEnabled", Boolean.toString(BoolArgumentType.getBool(ctx, "setpoint")));
                                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("reconnectEnabled set to" + SettingsManager.loadSetting("reconnectEnabled")), MinecraftClient.getInstance().player.getUuid());
                                    return 1;
                                })))
                .then(literal("damageLogoutEnabled")
                        .executes(ctx -> {
                            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("damageLogoutEnabled is set to " + SettingsManager.loadSetting("damageLogoutEnabled")), MinecraftClient.getInstance().player.getUuid());
                            return 1;
                        })
                        .then(argument("setpoint", bool())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("damageLogoutEnabled", Boolean.toString(BoolArgumentType.getBool(ctx, "setpoint")));
                                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("damageLogoutEnabled set to " + SettingsManager.loadSetting("damageLogoutEnabled")), MinecraftClient.getInstance().player.getUuid());
                                    return 1;
                                })))
                .then(literal("secondsBetweenReconnectionAttempts")
                        .executes(ctx -> {
                            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("secondsBetweenReconnectionAttempts is set to " + SettingsManager.loadSetting("secondsBetweenReconnectAttempts")), MinecraftClient.getInstance().player.getUuid());
                            return 1;
                        })
                        .then(argument("setpoint", integer())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("secondsBetweenReconnectAttempts", Integer.toString(IntegerArgumentType.getInteger(ctx, "setpoint")));
                                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("secondsBetweenReconnectionAttempts set to " + SettingsManager.loadSetting("secondsBetweenReconnectAttempts")), MinecraftClient.getInstance().player.getUuid());
                                    return 1;
                                })))
                .then(literal("reconnectAttemptNumber")
                        .executes(ctx -> {
                            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("reconnectAttemptNumber is set to " + SettingsManager.loadSetting("reconnectAttemptNumber")), MinecraftClient.getInstance().player.getUuid());
                            return 1;
                        })
                        .then(argument("setpoint", integer())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("reconnectAttemptNumber", Integer.toString(IntegerArgumentType.getInteger(ctx, "setpoint")));
                                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("reconnectAttemptNumber set to " + SettingsManager.loadSetting("reconnectAttemptNumber")), MinecraftClient.getInstance().player.getUuid());
                                    return 1;
                                })))
                .then(literal("damageLogoutTolerance")
                        .then(argument("setpoint", integer())
                                .executes(ctx -> {
                                    int setpoint = IntegerArgumentType.getInteger(ctx, "setpoint");
                                    if (setpoint <= 20) {
                                        SettingsManager.writeSetting("damageLogoutTolerance", Integer.toString(setpoint));
                                        MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("reconnectAttemptNumber set to " + SettingsManager.loadSetting("damageLogoutTolerance")), MinecraftClient.getInstance().player.getUuid());
                                    }
                                    return 1;
                                })))
                .then(literal("afkmode")
                        .then(literal("enable")
                                .executes(ctx -> {
                                    SettingsManager.activateAFKMode();
                                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("AFKMode enabled"), MinecraftClient.getInstance().player.getUuid());
                                    return 1;
                                }))
                        .then(literal("disable")
                                .executes(ctx -> {
                                    SettingsManager.disableAFKMode();
                                    MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText("AFKMode disabled"), MinecraftClient.getInstance().player.getUuid());
                                    return 1;
                                })));

        dispatcher.register(afkpeace);
    }

}