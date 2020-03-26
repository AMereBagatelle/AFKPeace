package net.bagatelle.afkpeace.settings;

import java.util.Properties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class SettingsManager {
    // * Settings File. DO NOT CHANGE UNLESS YOU KNOW WHAT YOU ARE DOING
    public static final String settingsFilePath = "afkpeace.properties";

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

            inputStream = SettingsManager.class.getClassLoader().getResourceAsStream(settingsFilePath);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new IOException("Could not find properties file");
            }

            inputStream.close();

            maxReconnectTries = Integer.parseInt(prop.getProperty("maxReconnectTries", "3"));
            secondsBetweenReconnectionAttempts = Integer.parseInt(prop.getProperty("secondsBetweenReconnectionAttempts", "10"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSetting(String setting, String setpoint) throws IOException {
        InputStream inputStream;
        OutputStream outputStream;
        Properties prop = new Properties();

        inputStream = SettingsManager.class.getClassLoader().getResourceAsStream(settingsFilePath);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new IOException("Could not get properties file");
        }

        inputStream.close();

        URL settingsFileURL = SettingsManager.class.getClassLoader().getResource(settingsFilePath);
        try {
            File settingsFile = new File(settingsFileURL.toURI());
            outputStream = new FileOutputStream(settingsFile);
        } catch (URISyntaxException e) {
            throw new IOException("URIException");
        }

        prop.setProperty(setting, setpoint);

        prop.store(outputStream, null);
        
        outputStream.close();
        loadSettings();
    }
}