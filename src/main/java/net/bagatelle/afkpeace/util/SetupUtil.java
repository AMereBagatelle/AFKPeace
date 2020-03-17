package net.bagatelle.afkpeace.util;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class SetupUtil {
	
	FabricKeyBinding toggleReconnectToServer;
	
	public void configureKeybinds() {
		final String keybindCategory = "AFKPeace";

		KeyBindingRegistry keyBindingRegistry = KeyBindingRegistry.INSTANCE;

		keyBindingRegistry.addCategory(keybindCategory);

		keyBindingRegistry.register(toggleReconnectToServer = FabricKeyBinding.Builder
			.create(
				new Identifier("afkpeace:togglereconnecttoserver"),
    			InputUtil.Type.KEYSYM,
    			GLFW.GLFW_KEY_UNKNOWN,
    			keybindCategory
			).build()); //
		keyBindingRegistry.register(toggleReconnectToServer); // Toggle reconnecting to server
	}

	public void activateKeybinds() {
		ClientTickCallback.EVENT.register(e -> {
			
		});
	}
}