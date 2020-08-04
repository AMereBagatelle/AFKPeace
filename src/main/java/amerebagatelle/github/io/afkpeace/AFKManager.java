package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.settings.SettingsManager;

public class AFKManager {
    public static AFKManager INSTANCE = new AFKManager();
    private boolean afk = false;
    private boolean wasAfk = false;
    public long afkTime = 0;

    public void tickAfkStatus() {
        afkTime++;

        afk = afkTime > 100000;

        if (afk && !wasAfk) {
            SettingsManager.activateAFKMode();
        } else if (!afk && wasAfk) {
            SettingsManager.disableAFKMode();
        }
        wasAfk = afk;
    }

    public boolean isAfk() {
        return afk;
    }
}
