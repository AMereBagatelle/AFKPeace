package amerebagatelle.github.io.afkpeace.config;

import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.QuiltConfig;

import java.util.List;

@SuppressWarnings("unchecked")
public class AFKPeaceConfigManager {
    private static final AFKPeaceConfig CONFIG = QuiltConfig.create(
            "afkpeace",
            "config",
            AFKPeaceConfig.class
    );

    public static final TrackedValue<Boolean> AUTO_AFK = (TrackedValue<Boolean>) CONFIG.getValue(List.of("toggles", "autoAfk"));
    public static final TrackedValue<Boolean> RECONNECT_ENABLED = (TrackedValue<Boolean>) CONFIG.getValue(List.of("toggles", "reconnectEnabled"));
    public static final TrackedValue<Boolean> DAMAGE_LOGOUT_ENABLED = (TrackedValue<Boolean>) CONFIG.getValue(List.of("toggles", "damageLogoutEnabled"));
    public static final TrackedValue<Boolean> FEATURES_ENABLED_INDICATOR = (TrackedValue<Boolean>) CONFIG.getValue(List.of("toggles", "featuresEnabledIndicator"));

    public static final TrackedValue<Integer> AUTO_AFK_TIMER_SECONDS = (TrackedValue<Integer>) CONFIG.getValue(List.of("configurations", "autoAfkTimerSeconds"));
    public static final TrackedValue<Boolean> RECONNECT_ON_DAMAGE_LOGOUT = (TrackedValue<Boolean>) CONFIG.getValue(List.of("configurations", "reconnectOnDamageLogout"));
    public static final TrackedValue<Integer> SECONDS_BETWEEN_RECONNECT_ATTEMPTS = (TrackedValue<Integer>) CONFIG.getValue(List.of("configurations", "secondsBetweenReconnectAttempts"));
    public static final TrackedValue<Integer> RECONNECT_ATTEMPT_NUMBER = (TrackedValue<Integer>) CONFIG.getValue(List.of("configurations", "reconnectAttemptNumber"));
    public static final TrackedValue<Integer> DAMAGE_LOGOUT_TOLERANCE = (TrackedValue<Integer>) CONFIG.getValue(List.of("configurations", "damageLogoutTolerance"));

    public static <T> void setConfigValue(TrackedValue<T> value, T setpoint) {
        value.setValue(setpoint, true);
    }
}
