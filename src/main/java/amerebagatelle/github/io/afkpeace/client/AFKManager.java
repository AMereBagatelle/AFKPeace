package amerebagatelle.github.io.afkpeace.client;

import amerebagatelle.github.io.afkpeace.common.SettingsManager;

public class AFKManager {
    public static AFKManager INSTANCE = new AFKManager();
    private boolean afk = false;
    private boolean wasAfk = false;
    public long afkTime = 0;
    private long lastUpdate = 0;

    public void tickAfkStatus() {
        if (System.nanoTime() - lastUpdate > Math.pow(10, 9)) {
            afkTime++;

            afk = afkTime > SettingsManager.loadIntSetting("autoAfkTimer");

            if (afk && !wasAfk) {
                SettingsManager.activateAFKMode();
            } else if (!afk && wasAfk) {
                SettingsManager.disableAFKMode();
            }
            wasAfk = afk;
            lastUpdate = System.nanoTime();
        }
    }

    public boolean isAfk() {
        return afk;
    }
}
