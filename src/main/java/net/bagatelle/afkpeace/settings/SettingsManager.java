package net.bagatelle.afkpeace.settings;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SettingsManager {
    // * Settings File. DO NOT CHANGE UNLESS YOU KNOW WHAT YOU ARE DOING
    public static final String settingsFilePath = "";

    // * Reconnection
    public static int maxReconnectTries;
    public static int secondsBetweenReconnectionAttempts;

    // * Active toggles
    public static boolean isReconnectOnTimeoutActive = false;
    public static boolean isDamageProtectActive = false;

    public static void loadSettings() {
        InputStream inputStream;

        try {
            Properties prop = new Properties();
            
            inputStream = new FileInputStream(settingsFilePath);

            prop.load(inputStream);

            inputStream.close();

            maxReconnectTries = Integer.parseInt(prop.getProperty("maxReconnectTries", "3"));
            secondsBetweenReconnectionAttempts = Integer.parseInt(prop.getProperty("secondsBetweenReconnectionAttempts", "10"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSetting(String setting, String setpoint) {
        OutputStream outputStream;

        try {
            Properties prop = new Properties();

            outputStream = new FileOutputStream(settingsFilePath);

            prop.setProperty(setting, setpoint);

            prop.store(outputStream, null);
            
            outputStream.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        loadSettings();
    }
}