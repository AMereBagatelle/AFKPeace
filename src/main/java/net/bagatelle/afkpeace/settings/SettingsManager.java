package net.bagatelle.afkpeace.settings;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SettingsManager {
    // * Settings File.  DO NOT CHANGE UNLESS YOU KNOW WHAT YOU ARE DOING
    public static final String settingsFilePath = "";

    // * Reconnection
    public static int maxReconnectTries;
    public static int secondsBetweenReconnectionAttempts;

    // * Active toggles
    public static boolean isReconnectOnTimeoutActive = false;
    public static boolean isDamageProtectActive = false;

    public void loadSettings() {
	    InputStream inputStream;

		try {
			Properties prop = new Properties();

			inputStream = getClass().getClassLoader().getResourceAsStream(settingsFilePath);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + settingsFilePath + "' not found in the classpath");
            }
            
            inputStream.close();
            
            maxReconnectTries = Integer.parseInt(prop.getProperty("maxReconnectTries", "3"));
            secondsBetweenReconnectionAttempts = Integer.parseInt(prop.getProperty("secondsBetweenReconnectionAttempts", "10"));

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void writeSetting(String setting, String setpoint) {
        OutputStream outputStream;

        try {
            Properties prop = new Properties();

            outputStream = new FileOutputStream(settingsFilePath);

            prop.setProperty(setting, setpoint);

            prop.store(outputStream, null);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}