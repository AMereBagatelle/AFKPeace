package amerebagatelle.github.io.afkpeace.gui;

import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.function.Predicate;

public class AFKPeaceConfig extends Screen {
    private final Predicate<String> numberFilter = (string) -> {
        if (string.length() == 0 || string.equals("-")) {
            return true;
        } else {
            try {
                Integer.parseInt(string);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    };

    private ButtonWidget autoAFKToggle;
    private ButtonWidget reconnectToggle;
    private ButtonWidget damageLogoutToggle;
    private ButtonWidget reconnectOnDamageToggle;

    private TextFieldWidget secondsBetweenAttemptsField;
    private TextFieldWidget reconnectAttemptNumberField;
    private TextFieldWidget damageLogoutAttemptField;

    public AFKPeaceConfig() {
        super(new LiteralText("AFKPeace Config"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        autoAFKToggle = addButton(new ButtonWidget(10, 30, 170, 20, new LiteralText(""), (onPress) -> {
            SettingsManager.writeSetting("autoAfk", Boolean.toString(!SettingsManager.loadBooleanSetting("autoAfk")));
        }));
        reconnectToggle = addButton(new ButtonWidget(10, 60, 170, 20, new LiteralText(""), (onPress) -> {
            SettingsManager.writeSetting("reconnectEnabled", Boolean.toString(!SettingsManager.loadBooleanSetting("reconnectEnabled")));
        }));
        damageLogoutToggle = addButton(new ButtonWidget(10, 90, 170, 20, new LiteralText(""), (onPress) -> {
            SettingsManager.writeSetting("damageLogoutEnabled", Boolean.toString(!SettingsManager.loadBooleanSetting("damageLogoutEnabled")));
        }));
        reconnectOnDamageToggle = addButton(new ButtonWidget(10, 120, 170, 20, new LiteralText(""), (onPress) -> {
            SettingsManager.writeSetting("reconnectOnDamageLogout", Boolean.toString(!SettingsManager.loadBooleanSetting("reconnectOnDamageLogout")));
        }));
        updateButtons();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawCenteredString(matrices, textRenderer, "AFKPeace Config", width / 2, 10, 16777215);
        updateButtons();
    }

    private void updateButtons() {
        autoAFKToggle.setMessage(getButtonBooleanText("AutoAFK", SettingsManager.loadBooleanSetting("autoAfk")));
        reconnectToggle.setMessage(getButtonBooleanText("reconnectEnabled", SettingsManager.loadBooleanSetting("reconnectEnabled")));
        damageLogoutToggle.setMessage(getButtonBooleanText("damageLogoutEnabled", SettingsManager.loadBooleanSetting("damageLogoutEnabled")));
        reconnectOnDamageToggle.setMessage(getButtonBooleanText("reconnectOnDamageLogout", SettingsManager.loadBooleanSetting("reconnectOnDamageLogout")));
    }

    private Text getButtonBooleanText(String option, boolean optionValue) {
        return new LiteralText(option + ": " + (optionValue ? "On" : "Off"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
