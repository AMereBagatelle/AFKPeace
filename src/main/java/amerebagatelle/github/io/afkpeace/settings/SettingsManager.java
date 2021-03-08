package amerebagatelle.github.io.afkpeace.settings;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;

public class SettingsManager {
    public static File settingsFile = new File("config/afkpeace.properties");

    public static final Settings settings = new Settings();
    public static final Settings settingsOverride = new Settings();

    public static void initSettings() {
        Field[] settingsList = Settings.class.getFields();
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
                    for (Field setting : settingsList) {
                        String settingType = setting.getType().toString();
                        Object setpoint = setting.get(settings);
                        String value = null;
                        if (settingType.equals("boolean")) {
                            value = Boolean.toString((Boolean) setpoint);
                        } else if (settingType.equals("int")) {
                            value = Integer.toString((Integer) setpoint);
                        }
                        prop.put(setting.getName(), value);
                    }
                    System.out.println("Test");

                    BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
                    prop.store(writer, null);
                    writer.flush();
                    writer.close();
                } else {
                    throw new IOException();
                }
            } catch (IOException | IllegalAccessException e) {
                throw new RuntimeException("Could not create settings file for AFKPeace!");
            }
        } else { // If the file does exist, make sure that it has all the settings and generate the ones that don't exist
            try {
                Properties prop = new Properties();
                prop.load(new BufferedReader(new FileReader(settingsFile)));
                for (Field setting : settingsList) {
                    if (prop.getProperty(setting.getName()) == null) {
                        String settingType = setting.getType().toString();
                        Object setpoint = setting.get(settings);
                        String value = null;
                        if (settingType.equals("boolean")) {
                            value = Boolean.toString((Boolean) setpoint);
                        } else if (settingType.equals("int")) {
                            value = Integer.toString((Integer) setpoint);
                        }
                        prop.put(setting.getName(), value);
                    }
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
                prop.store(writer, null);
                writer.flush();
                writer.close();

                loadSettingsToObject(prop);
            } catch (IOException | IllegalAccessException e) {
                throw new RuntimeException("Could not create settings file for AFKPeace!");
            }
        }
    }

    public static boolean applyOverride(boolean original, boolean override) {
        return !override && original;
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

            loadSettingsToObject(prop);
        } catch (IOException | IllegalAccessException e) {
            System.out.println("Can't write setting...");
        }
    }

    public static void loadSettingsToObject(Properties prop) throws IllegalAccessException {
        Field[] settingsList = Settings.class.getFields();
        for (Field setting : settingsList) {
            String settingType = setting.getType().toString();
            String setpoint = prop.getProperty(setting.getName());
            Object value = null;
            if (settingType.equals("boolean")) {
                value = Boolean.parseBoolean(setpoint);
            } else if (settingType.equals("int")) {
                value = Integer.parseInt(setpoint);
            }
            setting.set(settings, value);
        }
    }

    /**
     * Loads a setting from file
     *
     * @param setting Name of the setting to load.
     * @return The setting value.
     */
    public static String loadSettingFromFile(String setting) {
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

    public static boolean loadBooleanSettingFromFile(String setting) {
        return Boolean.parseBoolean(loadSettingFromFile(setting));
    }

    /**
     * Starts AFKMode.
     */
    public static void activateAFKMode() {
        settings.reconnectEnabled = true;
        settings.damageLogoutEnabled = true;
    }

    /**
     * Stops AFKMode.  Respects users' options.
     */
    public static void disableAFKMode() {
        if (!loadBooleanSettingFromFile("reconnectEnabled")) settings.reconnectEnabled = false;
        if (!loadBooleanSettingFromFile("damageLogoutEnabled")) settings.damageLogoutEnabled = false;
    }
}