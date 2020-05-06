package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.util.*;
import amerebagatelle.github.io.afkpeace.settings.*;
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
