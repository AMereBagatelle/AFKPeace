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
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

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
                            sendPrivateMessage(new TranslatableText("reconnectEnabled.get", SettingsManager.loadSetting("reconnectEnabled")));
                            return 1;
                        })
                        .then(argument("setpoint", bool())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("reconnectEnabled", Boolean.toString(BoolArgumentType.getBool(ctx, "setpoint")));
                                    sendPrivateMessage(new TranslatableText("reconnectEnabled.set", SettingsManager.loadSetting("reconnectEnabled")));
                                    return 1;
                                })))
                .then(literal("damageLogoutEnabled")
                        .executes(ctx -> {
                            sendPrivateMessage(new TranslatableText("damageLogoutEnabled.get", SettingsManager.loadSetting("damageLogoutEnabled")));
                            return 1;
                        })
                        .then(argument("setpoint", bool())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("damageLogoutEnabled", Boolean.toString(BoolArgumentType.getBool(ctx, "setpoint")));
                                    sendPrivateMessage(new TranslatableText("damageLogoutEnabled.set", SettingsManager.loadSetting("damageLogoutEnabled")));
                                    return 1;
                                })))
                .then(literal("autoAfk")
                        .executes(ctx -> {
                            sendPrivateMessage(new TranslatableText("autoAfk.get", SettingsManager.loadSetting("autoAfk")));
                            return 1;
                        })
                        .then(argument("setpoint", bool())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("autoAfk", Boolean.toString(BoolArgumentType.getBool(ctx, "setpoint")));
                                    sendPrivateMessage(new TranslatableText("autoAfk.set", SettingsManager.loadSetting("autoAfk")));
                                    return 1;
                                })))
                .then(literal("reconnectOnDamageLogout")
                        .executes(ctx -> {
                            sendPrivateMessage(new TranslatableText("reconnectOnDamageLogout.get", SettingsManager.loadSetting("reconnectOnDamageLogout")));
                            return 1;
                        })
                        .then(argument("setpoint", bool())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("reconnectOnDamageLogout", Boolean.toString(BoolArgumentType.getBool(ctx, "setpoint")));
                                    sendPrivateMessage(new TranslatableText("reconnectOnDamageLogout.set", SettingsManager.loadSetting("reconnectOnDamageLogout")));
                                    return 1;
                                })))
                .then(literal("secondsBetweenReconnectionAttempts")
                        .executes(ctx -> {
                            sendPrivateMessage(new TranslatableText("secondsBetweenReconnectionAttempts.get", SettingsManager.loadSetting("secondsBetweenReconnectAttempts")));
                            return 1;
                        })
                        .then(argument("setpoint", integer())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("secondsBetweenReconnectAttempts", Integer.toString(IntegerArgumentType.getInteger(ctx, "setpoint")));
                                    sendPrivateMessage(new TranslatableText("secondsBetweenReconnectionAttempts.set", SettingsManager.loadSetting("secondsBetweenReconnectAttempts")));
                                    return 1;
                                })))
                .then(literal("reconnectAttemptNumber")
                        .executes(ctx -> {
                            sendPrivateMessage(new TranslatableText("reconnectAttemptNumber.get", SettingsManager.loadSetting("reconnectAttemptNumber")));
                            return 1;
                        })
                        .then(argument("setpoint", integer())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("reconnectAttemptNumber", Integer.toString(IntegerArgumentType.getInteger(ctx, "setpoint")));
                                    sendPrivateMessage(new TranslatableText("reconnectAttemptNumber.set", SettingsManager.loadSetting("reconnectAttemptNumber")));
                                    return 1;
                                })))
                .then(literal("damageLogoutTolerance")
                        .executes(ctx -> {
                            sendPrivateMessage(new TranslatableText("damagelogouttolerance.set", SettingsManager.loadSetting("damageLogoutTolerance")));
                            return 1;
                        })
                        .then(argument("setpoint", integer())
                                .executes(ctx -> {
                                    int setpoint = IntegerArgumentType.getInteger(ctx, "setpoint");
                                    if (setpoint <= 20) {
                                        SettingsManager.writeSetting("damageLogoutTolerance", Integer.toString(setpoint));
                                        sendPrivateMessage(new TranslatableText("damagelogouttolerance.set", SettingsManager.loadSetting("damageLogoutTolerance")));
                                    }
                                    return 1;
                                })))
                .then(literal("afkmode")
                        .then(literal("enable")
                                .executes(ctx -> {
                                    SettingsManager.activateAFKMode();
                                    sendPrivateMessage(new TranslatableText("afkmode.enabled"));
                                    return 1;
                                }))
                        .then(literal("disable")
                                .executes(ctx -> {
                                    SettingsManager.disableAFKMode();
                                    sendPrivateMessage(new TranslatableText("afkmode.disabled"));
                                    return 1;
                                })));

        dispatcher.register(afkpeace);
    }

    /**
     * Sends message to client.
     *
     * @param message Message to send.
     */
    public void sendPrivateMessage(Text message) {
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.inGameHud.addChatMessage(MessageType.SYSTEM, message, mc.player.getUuid());
    }

}