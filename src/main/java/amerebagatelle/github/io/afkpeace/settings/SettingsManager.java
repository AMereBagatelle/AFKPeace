package amerebagatelle.github.io.afkpeace.settings;

import java.io.*;
import java.util.Properties;

public class SettingsManager {
    public static File settingsFile = new File("config/afkpeace.properties");

    public static final String[][] settings = {
            {"reconnectEnabled", "false"},
            {"secondsBetweenReconnectAttempts", "3"},
            {"reconnectAttemptNumber", "10"},
            {"damageLogoutEnabled", "false"},
            {"reconnectOnDamageLogout", "false"},
            {"damageLogoutTolerance", "20"},
            {"autoAfk", "false"}
    };

    /**
     * Initialize the settings file on startup.
     */
    public static void initSettings() {
        // Init settings file if it doesn't exist
        if (!settingsFile.exists()) {
            File configDir = new File("config/");
            if (!configDir.isDirectory()) {
                //noinspection ResultOfMethodCallIgnored
                configDir.mkdir();
            }
            try {
                boolean fileCreated = settingsFile.createNewFile();

                if (fileCreated) {
                    Properties prop = new Properties();
                    for (String[] setting : settings) {
                        prop.put(setting[0], setting[1]);
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
                    prop.store(writer, null);
                    writer.flush();
                    writer.close();
                } else {
                    throw new IOException();
                }
            } catch (IOException e) {
                throw new RuntimeException("Could not create settings file for AFKPeace!");
            }
        } else { // If the file does exist, make sure that it has all the settings and generate the ones that don't exist
            try {
                Properties prop = new Properties();
                prop.load(new BufferedReader(new FileReader(settingsFile)));
                for (String[] setting : settings) {
                    if (prop.getProperty(setting[0]) == null) {
                        prop.put(setting[0], setting[1]);
                    }
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
                prop.store(writer, null);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not create settings file for AFKPeace!");
            }
        }
    }

    /**
     * @param setting Name of the setting to load.
     * @return The setting value.
     */
    public static String loadSetting(String setting) {
        BufferedReader reader;
        Properties prop = new Properties();

        try {
            reader = new BufferedReader(new FileReader(settingsFile));
            prop.load(reader);
            reader.close();

            return prop.getProperty(setting);
        } catch (IOException e) {
            throw new RuntimeException("Can't read settings for AFKPeace!");
        }
    }

    /**
     * @param key      Name of setting to write to.
     * @param setpoint What to set the setting to.
     */
    public static void writeSetting(String key, String setpoint) {
        Properties prop = new Properties();
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(settingsFile));
            prop.load(reader);
            reader.close();

            prop.setProperty(key, setpoint);

            writer = new BufferedWriter(new FileWriter(settingsFile));
            prop.store(writer, null);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write setting...");
        }
    }

    /**
     * Starts AFKMode.
     */
    public static void activateAFKMode() {
        writeSetting("reconnectEnabled", "true");
        writeSetting("damageLogoutEnabled", "true");
    }

    /**
     * Stops AFKMode.
     */
    public static void disableAFKMode() {
        writeSetting("reconnectEnabled", "false");
        writeSetting("damageLogoutEnabled", "false");
    }
}