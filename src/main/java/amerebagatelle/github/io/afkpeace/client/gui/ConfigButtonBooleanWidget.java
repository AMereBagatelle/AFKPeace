package amerebagatelle.github.io.afkpeace.client.gui;

import amerebagatelle.github.io.afkpeace.client.settings.SettingsManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;

public class ConfigButtonBooleanWidget extends ButtonWidget {
    private final String option;
    private boolean value;

    public ConfigButtonBooleanWidget(int x, int y, int width, int height, String option) {
        super(x, y, width, height, new LiteralText(""), press -> {
        });

        this.option = option;
        value = SettingsManager.loadBooleanSetting(option);
        updateMessage();
    }

    @Override
    public void onPress() {
        value = !value;
        updateMessage();
    }

    private void updateMessage() {
        setMessage(new LiteralText(option + ": " + (value ? "On" : "Off")));
    }

    public void saveValue() {
        SettingsManager.writeSetting(option, Boolean.toString(value));
    }

    public int getRight() {
        return x + width;
    }
}
