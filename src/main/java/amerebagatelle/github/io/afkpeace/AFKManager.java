package amerebagatelle.github.io.afkpeace;

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

            boolean afk = afkTime > AFKPeaceClient.CONFIG.autoAfkTimerSeconds;

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
            wasAfk = afk;
            lastUpdate = System.nanoTime();
        }
    }

    public static boolean isAfk() {
        return isAfk;
    }
}
