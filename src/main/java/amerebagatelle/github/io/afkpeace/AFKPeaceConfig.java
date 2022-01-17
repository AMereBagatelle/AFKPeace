package amerebagatelle.github.io.afkpeace;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.Nullable;

public class AFKPeaceConfig implements Config {
    // Setting toggles
    @Comment("Turn all features on when AFK for longer than autoAfkTimer seconds.")
    public boolean autoAfk = false;
    @Comment("Allow the client to automatically reconnect to the server when disconnected by network issues.")
    public boolean reconnectEnabled = false;
    @Comment("Disconnect from the server when greater than damageLogoutThreshold damage is taken.")
    public boolean damageLogoutEnabled = false;
    @Comment("Places small text in upper left to indicate when AFKPeace features are enabled.")
    public boolean featuresEnabledIndicator = true;

    // Setting configurations
    @Comment("The amount of time in seconds before autoAfk is activated.")
    public int autoAfkTimerSeconds = 300;
    @Comment("Whether the client should reconnect after being logged out by the damage logout feature.")
    public boolean reconnectOnDamageLogout = false;
    @Comment("The amount of time in seconds between reconnection attempts.")
    public int secondsBetweenReconnectAttempts = 3;
    @Comment("The number of times the client will try to reconnect before giving up.")
    public int reconnectAttemptNumber = 10;
    @Comment("The amount of damage the player can take before being logged out.")
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
