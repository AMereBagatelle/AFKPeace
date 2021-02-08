package amerebagatelle.github.io.afkpeace.client;

import amerebagatelle.github.io.afkpeace.common.SettingsManager;

public class AFKManager {
    private static boolean afk = false;
    private static boolean wasAfk = false;
    public static long afkTime = 0;
    private static long lastUpdate = 0;

    public static void tickAfkStatus() {
        if (System.nanoTime() - lastUpdate > 1e+9) {
            afkTime++;

            afk = afkTime > SettingsManager.loadIntSetting("autoAfkTimer");

            if (afk && !wasAfk) {
                AFKPeaceClient.LOGGER.debug("AutoAFK on.");
                SettingsManager.activateAFKMode();
            } else if (!afk && wasAfk) {
                AFKPeaceClient.LOGGER.debug("AutoAFK off.");
                SettingsManager.disableAFKMode();
            }
            wasAfk = afk;
            lastUpdate = System.nanoTime();
        }
    }
}
