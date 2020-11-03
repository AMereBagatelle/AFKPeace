package amerebagatelle.github.io.afkpeace.client.gui;

import amerebagatelle.github.io.afkpeace.common.SettingsManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ConfigTextWidget extends TextFieldWidget {
    private final String option;

    public ConfigTextWidget(TextRenderer textRenderer, int x, int y, int width, int height, String option) {
        super(textRenderer, x, y, width, height, new LiteralText(""));
        this.option = option;
        setText(SettingsManager.loadSetting(option));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        drawStringWithShadow(matrices, textRenderer, option, x, y - 10, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void saveValue() {
        SettingsManager.writeSetting(option, getText());
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        SettingsManager.writeSetting(option, getText());
        return super.charTyped(chr, keyCode);
    }
}