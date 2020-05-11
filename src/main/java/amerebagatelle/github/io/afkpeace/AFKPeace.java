package amerebagatelle.github.io.afkpeace;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class AFKPeace implements ClientModInitializer {
	private final MinecraftClient mc = MinecraftClient.getInstance();

	public final ConnectionManager connectionManager = new ConnectionManager(mc);

	@Override
	public void onInitializeClient() {
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}
}
