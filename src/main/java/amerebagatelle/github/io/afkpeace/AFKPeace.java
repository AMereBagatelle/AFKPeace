package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

@Environment(EnvType.CLIENT)
public class AFKPeace implements ClientModInitializer {
	private static final MinecraftClient mc = MinecraftClient.getInstance();

	public static ServerInfo currentServerEntry;

	@Override
	public void onInitializeClient() {
		SettingsManager.initSettings();
	}
}
