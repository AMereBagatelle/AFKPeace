package amerebagatelle.github.io.afkpeace.config

import org.quiltmc.config.api.Config
import org.quiltmc.config.api.WrappedConfig

@Suppress("unused")
class AFKPeaceConfig : WrappedConfig() {
    val toggles = Toggles()
    val configurations: Configurations = Configurations()

    inner class Toggles : Config.Section {
        val autoAfk = false
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
}