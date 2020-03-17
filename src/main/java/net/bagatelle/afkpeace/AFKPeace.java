package net.bagatelle.afkpeace;

import net.bagatelle.afkpeace.util.SetupUtil;
import net.fabricmc.api.ClientModInitializer;

public class AFKPeace implements ClientModInitializer {
	public SetupUtil setupUtil = new SetupUtil();

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		setupUtil.configureKeybinds();
		setupUtil.activateKeybinds();
	}
}
