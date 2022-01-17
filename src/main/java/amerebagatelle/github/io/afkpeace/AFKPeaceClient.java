package amerebagatelle.github.io.afkpeace;

import draylar.omegaconfig.OmegaConfig;
import draylar.omegaconfiggui.OmegaConfigGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

@Environment(EnvType.CLIENT)
public class AFKPeaceClient implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("AFKPeace");
	public static final AFKPeaceConfig CONFIG = OmegaConfig.register(AFKPeaceConfig.class);
	public static final String MODID = "afkpeace";

	public static KeyBinding settingsKeybind;

	public static ServerInfo currentServerEntry;
	public static boolean disabled;

	@Override
	public void onInitializeClient() {
		if(Files.exists(FabricLoader.getInstance().getConfigDir().resolve("afkpeace.properties"))) recoverOldConfig();

		OmegaConfigGui.registerConfigScreen(CONFIG);

		settingsKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("afkpeace.keybind.settingsMenu", -1, "AFKPeace"));

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if(settingsKeybind.wasPressed()) {
				client.setScreen(OmegaConfigGui.getConfigScreenFactory(CONFIG).get(client.currentScreen));
			}
		});

		HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
			if((CONFIG.reconnectEnabled || CONFIG.damageLogoutEnabled) && CONFIG.featuresEnabledIndicator) {
				TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
				textRenderer.draw(matrices, I18n.translate("afkpeace.hud.featuresEnabled"), 10, 10, 0xFFFFFF);
			}
		});

		LOGGER.info("AFKPeace " + FabricLoader.getInstance().getModContainer(MODID).get().getMetadata().getVersion() + " Initialized");
	}

	public void recoverOldConfig() {
		BufferedReader reader;
		Properties prop = new Properties();

		try {
			reader = new BufferedReader(new FileReader("config/afkpeace.properties"));
			prop.load(reader);
			reader.close();

			CONFIG.autoAfk = Boolean.parseBoolean(prop.getProperty("autoAfk"));
			CONFIG.reconnectEnabled = Boolean.parseBoolean(prop.getProperty("reconnectEnabled"));
			CONFIG.damageLogoutEnabled = Boolean.parseBoolean(prop.getProperty("damageLogoutEnabled"));

			CONFIG.autoAfkTimerSeconds = Integer.parseInt(prop.getProperty("autoAfkTimer"));
			CONFIG.reconnectOnDamageLogout = Boolean.parseBoolean(prop.getProperty("reconnectOnDamageLogout"));
			CONFIG.secondsBetweenReconnectAttempts = Integer.parseInt(prop.getProperty("secondsBetweenReconnectAttempts"));
			CONFIG.reconnectAttemptNumber = Integer.parseInt(prop.getProperty("reconnectAttemptNumber"));
			CONFIG.damageLogoutTolerance = Integer.parseInt(prop.getProperty("damageLogoutTolerance"));

			CONFIG.save();

			Files.delete(FabricLoader.getInstance().getConfigDir().resolve("afkpeace.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Can't read settings for AFKPeace!");
		}
	}
}
