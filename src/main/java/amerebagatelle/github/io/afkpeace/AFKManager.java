package amerebagatelle.github.io.afkpeace;

import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

public class AFKManager {
    private static boolean wasAfk = false;
    public static long afkTime = 0;
    private static long lastUpdate = 0;

    private static boolean isAfk = false;

    public static void tickAfkStatus() {
        if (System.nanoTime() - lastUpdate > 1e+9) {
            afkTime++;

            boolean afk = afkTime > AFKPeaceConfigManager.AUTO_AFK_TIMER_SECONDS.value();

            if (afk && !wasAfk) {
                AFKPeaceClient.LOGGER.debug("AutoAFK on.");
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.translatable("afkpeace.afkmode.on"), Text.translatable("")));
                }
                isAfk = true;
            } else if (!afk && wasAfk) {
                AFKPeaceClient.LOGGER.debug("AutoAFK off.");
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.translatable("afkpeace.afkmode.off"), Text.translatable("")));
                }
                isAfk = false;
            }
            wasAfk = afk && AFKPeaceConfigManager.AUTO_AFK.value();
            lastUpdate = System.nanoTime();
        }
    }

    public static boolean isAfk() {
        return isAfk;
    }

    public static boolean damageLogoutOverride() {
        return isAfk && AFKPeaceConfigManager.AUTO_AFK_DAMAGE_LOGOUT_ENABLED.value();
    }

    public static boolean reconnectOverride() {
        return isAfk && AFKPeaceConfigManager.AUTO_AFK_RECONNECT_ENABLED.value();
    }
}
