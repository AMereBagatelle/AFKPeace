package net.bagatelle.afkpeace.settings;

public class SettingsManager {
    // * Reconnection
    public static int maxReconnectTries = 10;
    public static int secondsBetweenReconnectionAttempts = 3;

    // * Active toggles
    public static boolean isReconnectOnTimeoutActive = false;
    public static boolean isDamageProtectActive = false;
}