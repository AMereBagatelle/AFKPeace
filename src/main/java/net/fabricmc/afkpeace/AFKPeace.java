package net.fabricmc.afkpeace;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class AFKPeace implements ClientModInitializer {
	public final String keybindCategory = "AFKPeace";

	public FabricKeyBinding toggleReconnectToServer;

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		KeyBindingRegistry.INSTANCE.addCategory(keybindCategory);

		KeyBindingRegistry.INSTANCE.register(toggleReconnectToServer = FabricKeyBinding.Builder.create(
			new Identifier("afkpeace:togglereconnecttoserver"),
    		InputUtil.Type.KEYSYM,
    		GLFW.GLFW_KEY_Y,
    		keybindCategory
		).build());
		
	}
}
