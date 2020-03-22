package net.bagatelle.afkpeace.util;

import org.lwjgl.glfw.GLFW;

import net.bagatelle.afkpeace.AFKPeace;
import net.bagatelle.afkpeace.settings.SettingsManager;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.MessageType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class SetupUtil {
	
	private FabricKeyBinding toggleReconnectToServer;
	private FabricKeyBinding toggleDamageLogout;

	private boolean toggleReconnectWasPressed;
	private boolean toggleDamageLogoutWasPressed;
	
	public void configureKeybinds() {
		final String keybindCategory = "AFKPeace";

		KeyBindingRegistry keyBindingRegistry = KeyBindingRegistry.INSTANCE;

		keyBindingRegistry.addCategory(keybindCategory);

		// Keybind for reconnect feature
		keyBindingRegistry.register(toggleReconnectToServer = FabricKeyBinding.Builder
			.create(
				new Identifier("afkpeace:togglereconnecttoserver"),
    			InputUtil.Type.KEYSYM,
    			GLFW.GLFW_KEY_UNKNOWN,
    			keybindCategory
			).build());
		keyBindingRegistry.register(toggleReconnectToServer);
		// Keybind for damage logout feature
		keyBindingRegistry.register(toggleDamageLogout = FabricKeyBinding.Builder
			.create(
				new Identifier("afkpeace:toggledamagelogout"),
    			InputUtil.Type.KEYSYM,
    			GLFW.GLFW_KEY_UNKNOWN,
    			keybindCategory
			).build());
		keyBindingRegistry.register(toggleDamageLogout);
	}

	public void clientTickCallbackActivation() {
		ClientTickCallback.EVENT.register(e -> {
			MinecraftClient mc = MinecraftClient.getInstance();
			// * Keybind handling
			// Handling the toggle of the reconnect feature
			if(toggleReconnectToServer.isPressed() && !toggleReconnectWasPressed) {
				SettingsManager.isReconnectOnTimeoutActive = !SettingsManager.isReconnectOnTimeoutActive;
				mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("AutoReconnect " + SettingsManager.isReconnectOnTimeoutActive));
			}
			toggleReconnectWasPressed = toggleReconnectToServer.isPressed();
			// Handling the toggle of the logout on damage feature
			if(toggleDamageLogout.isPressed() && !toggleDamageLogoutWasPressed) {
				SettingsManager.isDamageProtectActive = !SettingsManager.isDamageProtectActive;
				mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("AutoLogoutOnDamage " + SettingsManager.isDamageProtectActive));
			}
			toggleDamageLogoutWasPressed = toggleDamageLogout.isPressed();
			// * Reconnect feature related stuff
			if(AFKPeace.stateVariables.canReconnect != 0) {
				AFKPeace.connectUtil.finishReconnect(AFKPeace.stateVariables.canReconnect, AFKPeace.stateVariables.currentServer);
				AFKPeace.stateVariables.canReconnect = 0;
			}
		});
	}
}