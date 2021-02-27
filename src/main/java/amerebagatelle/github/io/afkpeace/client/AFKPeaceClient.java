package amerebagatelle.github.io.afkpeace.client;

import amerebagatelle.github.io.afkpeace.common.SettingsManager;
import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
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
	public static final String MODID = "afkpeace";

	public static ServerInfo currentServerEntry;
	public static boolean disabled;

	@Override
	public void onInitializeClient() {
		SettingsManager.initSettings();
		LOGGER.info("AFKPeace " + FabricLoader.getInstance().getModContainer(MODID).get().getMetadata().getVersion() + " Initialized");

		DisableListenerRegistry.register(MODID, "autoafk", (value) -> {
		});
		DisableListenerRegistry.register(MODID, "reconnectenabled", (value) -> {
		});
	}
}
