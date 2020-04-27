package net.bagatelle.afkpeace;

import net.bagatelle.afkpeace.settings.SettingsManager;
import net.bagatelle.afkpeace.util.ConnectUtil;
import net.bagatelle.afkpeace.util.SetupUtil;
import net.bagatelle.afkpeace.util.StateVariables;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class AFKPeace implements ClientModInitializer {
	public static final String modId = "afkpeace";

	public static SetupUtil setupUtil = new SetupUtil();
	public static ConnectUtil connectUtil = new ConnectUtil();
	public static StateVariables stateVariables = new StateVariables();

	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		setupUtil.configureKeybinds();
		setupUtil.clientTickCallbackActivation();
		SettingsManager.loadSettings();
	}
}
