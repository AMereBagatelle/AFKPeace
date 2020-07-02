package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class AFKPeace implements ClientModInitializer {
	private static final MinecraftClient mc = MinecraftClient.getInstance();
	public static final Logger LOGGER = LogManager.getLogger();

	public static ServerInfo currentServerEntry;

	@Override
	public void onInitializeClient() {
		LOGGER.info("AFKPeace " + FabricLoader.getInstance().getModContainer("afkpeace").get().getMetadata().getVersion() + " Initialized");
		SettingsManager.initSettings();
	}
}
