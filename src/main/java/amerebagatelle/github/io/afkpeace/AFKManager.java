package amerebagatelle.github.io.afkpeace;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

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
                    MinecraftClient.getInstance().player.sendSystemMessage(new TranslatableText("afkpeace.afkmode.on"), Util.NIL_UUID);
                }
                isAfk = true;
            } else if (!afk && wasAfk) {
                AFKPeaceClient.LOGGER.debug("AutoAFK off.");
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendSystemMessage(new TranslatableText("afkpeace.afkmode.off"), Util.NIL_UUID);
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
