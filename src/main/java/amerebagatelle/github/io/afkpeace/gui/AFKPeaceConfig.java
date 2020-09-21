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
    private final Screen parent;

    private final Predicate<String> numberFilter = (string) -> {
        if (string.length() == 0) {
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
    private TextFieldWidget damageLogoutToleranceField;

    public AFKPeaceConfig(Screen parent) {
        super(new LiteralText("AFKPeace Config"));
        this.parent = parent;
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

        addButton(new ButtonWidget(width - 110, height - 30, 100, 20, new LiteralText("Exit"), (onPress) -> {
            MinecraftClient.getInstance().openScreen(parent);
        }));

        secondsBetweenAttemptsField = addChild(new TextFieldWidget(textRenderer, 10, 170, 170, 20, new LiteralText("")));
        secondsBetweenAttemptsField.setTextPredicate(numberFilter);
        secondsBetweenAttemptsField.setText(SettingsManager.loadSetting("secondsBetweenReconnectAttempts"));

        reconnectAttemptNumberField = addChild(new TextFieldWidget(textRenderer, 10, 220, 170, 20, new LiteralText("")));
        reconnectAttemptNumberField.setTextPredicate(numberFilter);
        reconnectAttemptNumberField.setText(SettingsManager.loadSetting("reconnectAttemptNumber"));

        damageLogoutToleranceField = addChild(new TextFieldWidget(textRenderer, 10, 270, 170, 20, new LiteralText("")));
        damageLogoutToleranceField.setTextPredicate(numberFilter);
        damageLogoutToleranceField.setText(SettingsManager.loadSetting("damageLogoutTolerance"));

        updateButtons();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawCenteredString(matrices, textRenderer, "AFKPeace Config", width / 2, 10, 16777215);

        drawStringWithShadow(matrices, textRenderer, "secondsBetweenReconnectAttempts", 10, 160, 16777215);
        secondsBetweenAttemptsField.render(matrices, mouseX, mouseY, delta);
        SettingsManager.writeSetting("secondsBetweenReconnectAttempts", secondsBetweenAttemptsField.getText());

        drawStringWithShadow(matrices, textRenderer, "reconnectAttemptNumber", 10, 210, 16777215);
        reconnectAttemptNumberField.render(matrices, mouseX, mouseY, delta);
        SettingsManager.writeSetting("reconnectAttemptNumber", reconnectAttemptNumberField.getText());

        drawStringWithShadow(matrices, textRenderer, "damageLogoutTolerance", 10, 260, 16777215);
        damageLogoutToleranceField.render(matrices, mouseX, mouseY, delta);
        SettingsManager.writeSetting("damageLogoutTolerance", damageLogoutToleranceField.getText());

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
