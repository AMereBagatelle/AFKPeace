package amerebagatelle.github.io.afkpeace.config

import org.quiltmc.config.api.Config
import org.quiltmc.config.api.WrappedConfig

@Suppress("unused")
class AFKPeaceConfig : WrappedConfig() {
    val toggles = Toggles()
    val configurations = Configurations()
    val afkMode = AFKMode()

    inner class Toggles : Config.Section {
        val reconnectEnabled = false
        val damageLogoutEnabled = false
        val featuresEnabledIndicator = true
    }

    inner class Configurations : Config.Section {
        val autoAfkTimerSeconds = 300
        val reconnectOnDamageLogout = false
        val secondsBetweenReconnectAttempts = 3
        val reconnectAttemptNumber = 10
        val damageLogoutTolerance = 20
    }

    inner class AFKMode : Config.Section {
        val autoAfk = false
        val autoAfkReconnectEnabled = false
        val autoAfkDamageLogoutEnabled = false
    }
}