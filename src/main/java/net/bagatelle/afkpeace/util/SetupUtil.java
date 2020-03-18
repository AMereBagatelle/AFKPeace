package net.bagatelle.afkpeace.util;

import org.lwjgl.glfw.GLFW;

import net.bagatelle.afkpeace.AFKPeace;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class SetupUtil {
	
	private FabricKeyBinding toggleReconnectToServer;

	private boolean toggleReconnectWasPressed;
	
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
			).build()); //
		keyBindingRegistry.register(toggleReconnectToServer);
	}

	public void activateKeybinds() {
		ClientTickCallback.EVENT.register(e -> {
			// Handling the toggle of the reconnect feature
			if(toggleReconnectToServer.isPressed() && !toggleReconnectWasPressed) {
				AFKPeace.reconnectCommand.setActive(!AFKPeace.reconnectCommand.getActive());
				System.out.println(AFKPeace.reconnectCommand.getActive());
			}
			toggleReconnectWasPressed = toggleReconnectToServer.isPressed();
		});
	}
}