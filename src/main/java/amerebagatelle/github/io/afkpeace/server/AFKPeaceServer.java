package amerebagatelle.github.io.afkpeace.server;

import amerebagatelle.github.io.afkpeace.common.SettingsManager;
import net.fabricmc.api.DedicatedServerModInitializer;

public class AFKPeaceServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        SettingsManager.initSettingsServer();
    }
}
