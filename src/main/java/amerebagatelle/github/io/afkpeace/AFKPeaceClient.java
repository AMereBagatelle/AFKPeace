package amerebagatelle.github.io.afkpeace;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.config.QuiltConfig;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

@Environment(EnvType.CLIENT)
public class AFKPeaceClient implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("AFKPeace");
	public static final String MODID = "afkpeace";

	public static final AFKPeaceConfig CONFIG = QuiltConfig.create(
			"afkpeace",
			"config",
			AFKPeaceConfig.class
	);

	public static KeyBinding settingsKeybind;

	public static ServerInfo currentServerEntry;
	public static Screen loginScreen;

	public static boolean disabled;

	@Override
	public void onInitializeClient(ModContainer mod) {
		settingsKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("afkpeace.keybind.settingsMenu", -1, "AFKPeace"));

		ClientTickEvents.END.register((client) -> {
			if(settingsKeybind.wasPressed()) {
				// TODO: reimplement settings keybind
			}

			if (AFKPeaceClient.CONFIG.autoAfk) {
				AFKManager.tickAfkStatus();
			}
		});

		HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
			if((CONFIG.reconnectEnabled || CONFIG.damageLogoutEnabled || AFKManager.isAfk()) && CONFIG.featuresEnabledIndicator) {
				TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
				textRenderer.draw(matrices, I18n.translate("afkpeace.hud.featuresEnabled"), 10, 10, 0xFFFFFF);
			}
		});

		ClientPlayConnectionEvents.JOIN.register((networkHandler, packetSender, client) -> currentServerEntry = client.getCurrentServerEntry());

		LOGGER.info("AFKPeace " + mod.metadata().version() + " Initialized");
	}
}
