package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager;
import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.resource.language.I18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;

public class AFKPeaceClient implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("AFKPeace");
	@SuppressWarnings("unused")
	public static final String MODID = "afkpeace";

	public static KeyBind settingsKeybind;

	public static ServerInfo currentServerEntry;
	public static Screen loginScreen;

	public static boolean disabled;

	@Override
	public void onInitializeClient(ModContainer mod) {
		settingsKeybind = KeyBindingHelper.registerKeyBinding(new KeyBind("afkpeace.keybind.settingsMenu", -1, "AFKPeace"));

		ClientTickEvents.END.register((client) -> {
			if(settingsKeybind.wasPressed()) {
				client.setScreen(new AFKPeaceConfigScreen(client.currentScreen));
			}

			if (AFKPeaceConfigManager.AUTO_AFK.value()) {
				AFKManager.tickAfkStatus();
			}
		});

		HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
			if ((AFKPeaceConfigManager.AUTO_AFK.value() || AFKPeaceConfigManager.DAMAGE_LOGOUT_ENABLED.value() || AFKManager.isAfk()) && AFKPeaceConfigManager.FEATURES_ENABLED_INDICATOR.value()) {
				TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
				textRenderer.draw(matrices, I18n.translate("afkpeace.hud.featuresEnabled"), 10, 10, 0xFFFFFF);
			}
		});

		ClientPlayConnectionEvents.JOIN.register((networkHandler, packetSender, client) -> currentServerEntry = client.getCurrentServerEntry());

		LOGGER.info("AFKPeace " + mod.metadata().version().raw() + " Initialized");
	}
}
