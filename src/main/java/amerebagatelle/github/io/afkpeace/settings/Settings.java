package amerebagatelle.github.io.afkpeace.settings;

public class Settings {
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
}
