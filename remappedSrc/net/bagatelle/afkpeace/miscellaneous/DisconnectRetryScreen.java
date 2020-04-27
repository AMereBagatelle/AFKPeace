package net.bagatelle.afkpeace.miscellaneous;

import java.util.Iterator;
import java.util.List;

import net.bagatelle.afkpeace.AFKPeace;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class DisconnectRetryScreen extends Screen {

    private final Text reason;
    private List<Text> reasonFormatted;
    private final Screen parent;
    private int reasonHeight;
    private ServerInfo serverInfo;

    public DisconnectRetryScreen(Screen parent, String title, Text reason, ServerInfo serverInfo) {
        super(new TranslatableText(title));
        this.parent = parent;
        this.reason = reason;
        this.serverInfo = serverInfo;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        this.reasonFormatted = this.textRenderer.wrapLines(this.reason, this.width - 50);
        int var10001 = this.reasonFormatted.size();
        this.textRenderer.getClass();
        this.reasonHeight = var10001 * 9;
        int var10003 = this.width / 2 - 100;
        int var10004 = this.height / 2 + this.reasonHeight / 2;
        this.textRenderer.getClass();
        this.addButton(new ButtonWidget(var10003, Math.min(var10004 + 9, this.height - 30), 200, 20, new TranslatableText("gui.toMenu"), (buttonWidget) -> {
            this.client.openScreen(this.parent);
        }));
        // Attempts to reconnect to the server you got disconnected from
        this.addButton(new ButtonWidget(var10003, Math.min(var10004 + 9, this.height - 30) + 20, 200, 20, new LiteralText("Reconnect"), (buttonWidget) -> {
            AFKPeace.connectUtil.connectToServer(serverInfo);
        }));
    }
    
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        TextRenderer var10002 = this.textRenderer;
        Text var10003 = this.title;
        int var10004 = this.width / 2;
        int var10005 = this.height / 2 - this.reasonHeight / 2;
        this.textRenderer.getClass();
        this.method_27534(matrices, var10002, var10003, var10004, var10005 - 9 * 2, 11184810);
        int i = this.height / 2 - this.reasonHeight / 2;
        if (this.reasonFormatted != null) {
            for(Iterator var6 = this.reasonFormatted.iterator(); var6.hasNext(); i += 9) {
                Text text = (Text)var6.next();
                this.method_27534(matrices, this.textRenderer, text, this.width / 2, i, 16777215);
                this.textRenderer.getClass();
            }
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

}