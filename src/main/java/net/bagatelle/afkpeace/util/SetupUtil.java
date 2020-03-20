package net.bagatelle.afkpeace.util;

import org.lwjgl.glfw.GLFW;

import net.bagatelle.afkpeace.AFKPeace;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
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

	public void activateKeybinds() {
		ClientTickCallback.EVENT.register(e -> {
			MinecraftClient mc = MinecraftClient.getInstance();
			// Handling the toggle of the reconnect feature
			if(toggleReconnectToServer.isPressed() && !toggleReconnectWasPressed) {
				AFKPeace.activeStates.isReconnectOnTimeoutActive = !AFKPeace.activeStates.isReconnectOnTimeoutActive;
				mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("AutoReconnect " + AFKPeace.activeStates.isReconnectOnTimeoutActive));
			}
			toggleReconnectWasPressed = toggleReconnectToServer.isPressed();
			// Handling the toggle of the logout on damage feature
			if(toggleDamageLogout.isPressed() && !toggleDamageLogoutWasPressed) {
				AFKPeace.activeStates.isDamageProtectActive = !AFKPeace.activeStates.isDamageProtectActive;
				mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("AutoLogoutOnDamage " + AFKPeace.activeStates.isDamageProtectActive));
			}
			toggleDamageLogoutWasPressed = toggleDamageLogout.isPressed();
		});
	}
}