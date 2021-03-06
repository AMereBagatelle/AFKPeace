package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
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

		// Disabled until Disabler is put on a maven somewhere
		/*
		MinecraftClient client = MinecraftClient.getInstance();
		DisableListenerRegistry.register(MODID, "autoafk", (value) -> {
			SettingsManager.settingsOverride.autoAfk = value;
			if (client.player != null)
				client.player.sendSystemMessage(new TranslatableText("afkpeace.override.autoAfk"), Util.NIL_UUID);
		});
		DisableListenerRegistry.register(MODID, "reconnectenabled", (value) -> {
			SettingsManager.settingsOverride.reconnectEnabled = value;
			if (client.player != null)
				client.player.sendSystemMessage(new TranslatableText("afkpeace.override.reconnectEnabled"), Util.NIL_UUID);
		});
		DisableListenerRegistry.register(MODID, "damagelogout", (value) -> {
			SettingsManager.settingsOverride.damageLogoutEnabled = value;
			if (client.player != null)
				client.player.sendSystemMessage(new TranslatableText("afkpeace.override.damageLogoutEnabled"), Util.NIL_UUID);
		});
		 */
	}
}
