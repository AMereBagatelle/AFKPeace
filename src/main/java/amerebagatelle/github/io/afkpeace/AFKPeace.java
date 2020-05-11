package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

@Environment(EnvType.CLIENT)
public class AFKPeace implements ClientModInitializer {
	private final MinecraftClient mc = MinecraftClient.getInstance();

	public final ConnectionManager connectionManager = new ConnectionManager(mc);

	public static boolean isReconnecting = false;
	public static ServerInfo currentServerEntry;

	@Override
	public void onInitializeClient() {
		SettingsManager.initSettings();
		ClientTickCallback.EVENT.register(event -> {
			if (isReconnecting) {
				connectionManager.finishReconnect();
				isReconnecting = false;
			}
		});
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	// Dev notes
	// TODO: Mixin to DisconnectScreen and add the reconnect button
}
