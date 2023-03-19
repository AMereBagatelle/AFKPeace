package amerebagatelle.github.io.afkpeace.config;

import org.quiltmc.config.api.WrappedConfig;

@SuppressWarnings({"unused"})
public class AFKPeaceConfig extends WrappedConfig {
    public final Toggles toggles = new Toggles();
    public final Configurations configurations = new Configurations();

    public class Toggles implements Section {
        public final boolean autoAfk = false;
        public final boolean reconnectEnabled = false;
        public final boolean damageLogoutEnabled = false;
        public final boolean featuresEnabledIndicator = true;
    }

    public class Configurations implements Section {
        public final int autoAfkTimerSeconds = 300;
        public final boolean reconnectOnDamageLogout = false;
        public final int secondsBetweenReconnectAttempts = 3;
        public final int reconnectAttemptNumber = 10;
        public final int damageLogoutTolerance = 20;
    }
}
