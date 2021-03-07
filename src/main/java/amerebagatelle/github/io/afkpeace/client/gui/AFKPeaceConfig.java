package amerebagatelle.github.io.afkpeace.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import java.util.function.Predicate;

public class AFKPeaceConfig extends Screen {
    private final Screen parent;

    private final Predicate<String> numberFilter = (string) -> string.matches("\\d+") || string.isEmpty();

    private ConfigButtonBooleanWidget autoAFKToggle;
    private ConfigButtonBooleanWidget reconnectToggle;
    private ConfigButtonBooleanWidget damageLogoutToggle;
    private ConfigButtonBooleanWidget reconnectOnDamageToggle;

    private ConfigTextWidget autoAFKTimeField;
    private ConfigTextWidget secondsBetweenAttemptsField;
    private ConfigTextWidget reconnectAttemptNumberField;
    private ConfigTextWidget damageLogoutToleranceField;

    private ButtonWidget confirm;

    public AFKPeaceConfig(Screen parent) {
        super(new TranslatableText("afkpeace.config.title"));
        this.parent = parent;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        int buttonWidth = 175;
        autoAFKToggle = addButton(new ConfigButtonBooleanWidget(10, 30, buttonWidth, 20, "autoAfk"));
        reconnectToggle = addButton(new ConfigButtonBooleanWidget(10, 60, buttonWidth, 20, "reconnectEnabled"));
        damageLogoutToggle = addButton(new ConfigButtonBooleanWidget(10, 90, buttonWidth, 20, "damageLogoutEnabled"));
        reconnectOnDamageToggle = addButton(new ConfigButtonBooleanWidget(10, 120, buttonWidth, 20, "reconnectOnDamageLogout"));

        int fieldOffset = 20;
        autoAFKTimeField = addChild(new ConfigTextWidget(textRenderer, autoAFKToggle.getRight() + fieldOffset, 40, 170, 20, "autoAfkTimer"));
        autoAFKTimeField.setTextPredicate(numberFilter);

        secondsBetweenAttemptsField = addChild(new ConfigTextWidget(textRenderer, autoAFKToggle.getRight() + fieldOffset, 80, 170, 20, "secondsBetweenReconnectAttempts"));
        secondsBetweenAttemptsField.setTextPredicate(numberFilter);

        reconnectAttemptNumberField = addChild(new ConfigTextWidget(textRenderer, autoAFKToggle.getRight() + fieldOffset, 120, 170, 20, "reconnectAttemptNumber"));
        reconnectAttemptNumberField.setTextPredicate(numberFilter);

        damageLogoutToleranceField = addChild(new ConfigTextWidget(textRenderer, autoAFKToggle.getRight() + fieldOffset, 160, 170, 20, "damageLogoutTolerance"));
        damageLogoutToleranceField.setTextPredicate(numberFilter);

        addButton(new ButtonWidget(width - 110, height - 30, 100, 20, new TranslatableText("afkpeace.button.cancel"), (onPress) -> client.openScreen(parent)));

        confirm = addButton(new ButtonWidget(width - 220, height - 30, 100, 20, new TranslatableText("afkpeace.button.confirm"), (onPress) -> {
            autoAFKToggle.saveValue();
            reconnectToggle.saveValue();
            damageLogoutToggle.saveValue();
            reconnectOnDamageToggle.saveValue();

            autoAFKTimeField.saveValue();
            secondsBetweenAttemptsField.saveValue();
            reconnectAttemptNumberField.saveValue();
            damageLogoutToleranceField.saveValue();

            client.openScreen(parent);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawStringWithShadow(matrices, textRenderer, I18n.translate("afkpeace.config.title"), 20, 10, 16777215);

        autoAFKTimeField.render(matrices, mouseX, mouseY, delta);

        secondsBetweenAttemptsField.render(matrices, mouseX, mouseY, delta);

        reconnectAttemptNumberField.render(matrices, mouseX, mouseY, delta);

        damageLogoutToleranceField.render(matrices, mouseX, mouseY, delta);

        confirm.active = autoAFKTimeField.getText().matches("\\d+") && secondsBetweenAttemptsField.getText().matches("\\d+") && reconnectAttemptNumberField.getText().matches("\\d+") && damageLogoutToleranceField.getText().matches("\\d+");
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
