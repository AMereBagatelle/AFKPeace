package amerebagatelle.github.io.afkpeace.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

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

    private ConfigButtonBooleanWidget autoAFKToggle;
    private ConfigButtonBooleanWidget reconnectToggle;
    private ConfigButtonBooleanWidget damageLogoutToggle;
    private ConfigButtonBooleanWidget reconnectOnDamageToggle;

    private ConfigTextWidget secondsBetweenAttemptsField;
    private ConfigTextWidget reconnectAttemptNumberField;
    private ConfigTextWidget damageLogoutToleranceField;

    public AFKPeaceConfig(Screen parent) {
        super(new LiteralText("AFKPeace Config"));
        this.parent = parent;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        autoAFKToggle = addButton(new ConfigButtonBooleanWidget(10, 30, 170, 20, "autoAfk"));
        reconnectToggle = addButton(new ConfigButtonBooleanWidget(10, 60, 170, 20, "reconnectEnabled"));
        damageLogoutToggle = addButton(new ConfigButtonBooleanWidget(10, 90, 170, 20, "damageLogoutEnabled"));
        reconnectOnDamageToggle = addButton(new ConfigButtonBooleanWidget(10, 120, 170, 20, "reconnectOnDamageLogout"));

        secondsBetweenAttemptsField = addChild(new ConfigTextWidget(textRenderer, autoAFKToggle.getRight() + 20, 40, 170, 20, "secondsBetweenReconnectAttempts"));
        secondsBetweenAttemptsField.setTextPredicate(numberFilter);

        reconnectAttemptNumberField = addChild(new ConfigTextWidget(textRenderer, autoAFKToggle.getRight() + 20, 80, 170, 20, "reconnectAttemptNumber"));
        reconnectAttemptNumberField.setTextPredicate(numberFilter);

        damageLogoutToleranceField = addChild(new ConfigTextWidget(textRenderer, autoAFKToggle.getRight() + 20, 120, 170, 20, "damageLogoutTolerance"));
        damageLogoutToleranceField.setTextPredicate(numberFilter);

        addButton(new ButtonWidget(width - 110, height - 30, 100, 20, new LiteralText("Cancel"), (onPress) -> {
            MinecraftClient.getInstance().openScreen(parent);
        }));

        addButton(new ButtonWidget(width - 220, height - 30, 100, 20, new LiteralText("Confirm"), (onPress) -> {
            autoAFKToggle.saveValue();
            reconnectToggle.saveValue();
            damageLogoutToggle.saveValue();
            reconnectOnDamageToggle.saveValue();

            secondsBetweenAttemptsField.saveValue();
            reconnectAttemptNumberField.saveValue();
            damageLogoutToleranceField.saveValue();

            MinecraftClient.getInstance().openScreen(parent);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawCenteredString(matrices, textRenderer, "AFKPeace Config", width / 2, 10, 16777215);

        secondsBetweenAttemptsField.render(matrices, mouseX, mouseY, delta);

        reconnectAttemptNumberField.render(matrices, mouseX, mouseY, delta);

        damageLogoutToleranceField.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
