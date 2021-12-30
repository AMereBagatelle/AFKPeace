package amerebagatelle.github.io.afkpeace;

import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.Nullable;

public class AFKPeaceConfig implements Config {
    // Setting toggles
    public boolean autoAfk = false;
    public boolean reconnectEnabled = false;
    public boolean damageLogoutEnabled = false;

    // Setting configurations
    public int autoAfkTimer = 300;
    public boolean reconnectOnDamageLogout = false;
    public int secondsBetweenReconnectAttempts = 3;
    public int reconnectAttemptNumber = 10;
    public int damageLogoutTolerance = 20;

    @Override
    public String getName() {
        return "afkpeace";
    }

    @Override
    public @Nullable String getModid() {
        return "afkpeace";
    }
}
