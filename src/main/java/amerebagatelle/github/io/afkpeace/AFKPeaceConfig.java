package amerebagatelle.github.io.afkpeace;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.config.api.WrappedConfig;

public class AFKPeaceConfig extends WrappedConfig {
    // Setting toggles
    public final boolean autoAfk = false;
    public final boolean reconnectEnabled = false;
    public final boolean damageLogoutEnabled = false;
    public final boolean featuresEnabledIndicator = true;

    // Setting configurations
    public final int autoAfkTimerSeconds = 300;
    public final boolean reconnectOnDamageLogout = false;
    public final int secondsBetweenReconnectAttempts = 3;
    public final int reconnectAttemptNumber = 10;
    public final int damageLogoutTolerance = 20;
}
