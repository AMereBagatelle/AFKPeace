package amerebagatelle.github.io.afkpeace

import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager
import net.minecraft.client.MinecraftClient
import net.minecraft.client.toast.SystemToast
import net.minecraft.text.Text

object AFKManager {
    private var wasAfk = false

    @JvmField
    var afkTime: Long = 0
    private var lastUpdate: Long = 0

    @JvmStatic
    var isAfk = false

    @JvmStatic
    fun tickAfkStatus() {
        if (System.nanoTime() - lastUpdate > 1e+9) {
            afkTime++
            val afk = afkTime > AFKPeaceConfigManager.AUTO_AFK_TIMER_SECONDS.value()
            if (afk && !wasAfk) {
                AFKPeaceClient.LOGGER.info("AutoAFK on.")
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().toastManager.add(
                        SystemToast(
                            SystemToast.Type.NARRATOR_TOGGLE,
                            Text.translatable("afkpeace.afkmode.on"),
                            Text.translatable("")
                        )
                    )
                }
                isAfk = true
            } else if (!afk && wasAfk) {
                AFKPeaceClient.LOGGER.info("AutoAFK off.")
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().toastManager.add(
                        SystemToast(
                            SystemToast.Type.NARRATOR_TOGGLE,
                            Text.translatable("afkpeace.afkmode.off"),
                            Text.translatable("")
                        )
                    )
                }
                isAfk = false
            }
            wasAfk = afk && AFKPeaceConfigManager.AUTO_AFK.value()
            lastUpdate = System.nanoTime()
        }
    }

    @JvmStatic
    fun damageLogoutOverride(): Boolean {
        return isAfk && AFKPeaceConfigManager.AUTO_AFK_DAMAGE_LOGOUT_ENABLED.value()
    }

    @JvmStatic
    fun reconnectOverride(): Boolean {
        return isAfk && AFKPeaceConfigManager.AUTO_AFK_RECONNECT_ENABLED.value()
    }
}