package amerebagatelle.github.io.afkpeace.commands;

import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.argument;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;

public class ConfigCommand implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        LiteralArgumentBuilder<CottonClientCommandSource> afkpeace = literal("afkpeace")
                .then(literal("reconnectEnabled")
                        .then(argument("setpoint", bool())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("reconnectEnabled", Boolean.toString(BoolArgumentType.getBool(ctx, "setpoint")));
                                    return 1;
                                })))
                .then(literal("damageLogoutEnabled")
                        .then(argument("setpoint", bool())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("damageLogoutEnabled", Boolean.toString(BoolArgumentType.getBool(ctx, "setpoint")));
                                    return 1;
                                })))
                .then(literal("secondsBetweenReconnectionAttempts"))
                .then(argument("setpoint", integer())
                        .executes(ctx -> {
                            SettingsManager.writeSetting("secondsBetweenReconnectionAttempts", Integer.toString(IntegerArgumentType.getInteger(ctx, "setpoint")));
                            return 1;
                        }))
                .then(literal("reconnectAttemptNumber")
                        .then(argument("setpoint", integer())
                                .executes(ctx -> {
                                    SettingsManager.writeSetting("reconnectAttemptNumber", Integer.toString(IntegerArgumentType.getInteger(ctx, "setpoint")));
                                    return 1;
                                })));

        dispatcher.register(afkpeace);
    }

}