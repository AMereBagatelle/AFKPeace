package amerebagatelle.github.io.afkpeace.client.gui;

import amerebagatelle.github.io.afkpeace.common.SettingsManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class ConfigButtonBooleanWidget extends ButtonWidget {
    private final String option;
    private boolean value;

    public ConfigButtonBooleanWidget(int x, int y, int width, int height, String option) {
        super(x, y, width, height, new LiteralText(""), press -> {
        });

        this.option = option;
        value = SettingsManager.loadBooleanSettingFromFile(option);
        updateMessage();
    }

    @Override
    public void onPress() {
        value = !value;
        updateMessage();
    }

    private void updateMessage() {
        setMessage(new TranslatableText("afkpeace.option." + option, value ? I18n.translate("options.on") : I18n.translate("options.off")));
    }

    public void saveValue() {
        SettingsManager.writeSetting(option, Boolean.toString(value));
    }

    public int getRight() {
        return x + width;
    }
}
