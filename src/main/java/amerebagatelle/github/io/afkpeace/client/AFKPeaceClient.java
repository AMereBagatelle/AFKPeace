package amerebagatelle.github.io.afkpeace.client;

import amerebagatelle.github.io.afkpeace.common.SettingsManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class AFKPeaceClient implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("AFKPeace");

	public static ServerInfo currentServerEntry;
	public static boolean disabled;

	@Override
	public void onInitializeClient() {
		LOGGER.info("AFKPeace " + FabricLoader.getInstance().getModContainer("afkpeace").get().getMetadata().getVersion() + " Initialized");
		SettingsManager.initSettingsClient();
	}
}
