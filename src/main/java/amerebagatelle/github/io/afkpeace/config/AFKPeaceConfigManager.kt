package amerebagatelle.github.io.afkpeace.config

import org.quiltmc.config.api.values.TrackedValue
import org.quiltmc.loader.api.config.QuiltConfig

object AFKPeaceConfigManager {
    private val CONFIG = QuiltConfig.create(
        "afkpeace",
        "config",
        AFKPeaceConfig::class.java
    )

    @JvmField
    val RECONNECT_ENABLED = CONFIG.getValue(listOf("toggles", "reconnectEnabled")) as TrackedValue<Boolean>

    @JvmField
    val DAMAGE_LOGOUT_ENABLED = CONFIG.getValue(listOf("toggles", "damageLogoutEnabled")) as TrackedValue<Boolean>

    @JvmField
    val FEATURES_ENABLED_INDICATOR =
        CONFIG.getValue(listOf("toggles", "featuresEnabledIndicator")) as TrackedValue<Boolean>

    @JvmField
    val AUTO_AFK_TIMER_SECONDS = CONFIG.getValue(listOf("configurations", "autoAfkTimerSeconds")) as TrackedValue<Int>

    @JvmField
    val RECONNECT_ON_DAMAGE_LOGOUT =
        CONFIG.getValue(listOf("configurations", "reconnectOnDamageLogout")) as TrackedValue<Boolean>

    @JvmField
    val SECONDS_BETWEEN_RECONNECT_ATTEMPTS =
        CONFIG.getValue(listOf("configurations", "secondsBetweenReconnectAttempts")) as TrackedValue<Int>

    @JvmField
    val RECONNECT_ATTEMPT_NUMBER =
        CONFIG.getValue(listOf("configurations", "reconnectAttemptNumber")) as TrackedValue<Int>

    @JvmField
    val DAMAGE_LOGOUT_TOLERANCE =
        CONFIG.getValue(listOf("configurations", "damageLogoutTolerance")) as TrackedValue<Int>

    @JvmField
    val AUTO_AFK = CONFIG.getValue(listOf("afkMode", "autoAfk")) as TrackedValue<Boolean>

    @JvmField
    val AUTO_AFK_RECONNECT_ENABLED =
        CONFIG.getValue(listOf("afkMode", "autoAfkReconnectEnabled")) as TrackedValue<Boolean>

    @JvmField
    val AUTO_AFK_DAMAGE_LOGOUT_ENABLED =
        CONFIG.getValue(listOf("afkMode", "autoAfkDamageLogoutEnabled")) as TrackedValue<Boolean>

    @JvmStatic
    fun <T : Any> setConfigValue(value: TrackedValue<T>, setpoint: T) {
        value.setValue(setpoint, true)
    }
}