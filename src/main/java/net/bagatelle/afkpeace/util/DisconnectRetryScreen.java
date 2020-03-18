package net.bagatelle.afkpeace.util;

import java.util.Iterator;
import java.util.List;

import net.bagatelle.afkpeace.AFKPeace;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class DisconnectRetryScreen extends Screen {

    private final Text reason;
    private List<String> reasonFormatted;
    private final Screen parent;
    private int reasonHeight;
    private ServerInfo serverInfo;
    private boolean reconnectButton;

    public DisconnectRetryScreen(Screen parent, String title, Text reason, ServerInfo serverInfo, boolean reconnectButton) {
        super(new TranslatableText(title, new Object[0]));
        this.parent = parent;
        this.reason = reason;
        this.serverInfo = serverInfo;
        this.reconnectButton = reconnectButton;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        this.reasonFormatted = this.font.wrapStringToWidthAsList(this.reason.asFormattedString(), this.width - 50);
        int var10001 = this.reasonFormatted.size();
        this.font.getClass();
        this.reasonHeight = var10001 * 9;
        int var10003 = this.width / 2 - 100;
        int var10004 = this.height / 2 + this.reasonHeight / 2;
        this.font.getClass();
        this.addButton(new ButtonWidget(var10003, Math.min(var10004 + 9, this.height - 30), 200, 20, I18n.translate("gui.toMenu"), (buttonWidget) -> {
            this.minecraft.openScreen(this.parent);
        }));
        if(reconnectButton) {
            this.addButton(new ButtonWidget(var10003, Math.min(var10004 + 9, this.height - 30) + 20, 200, 20, "Reconnect", (buttonWidget) -> {
                AFKPeace.connectUtil.connectToServer(serverInfo);
            }));
        }
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        // * Don't touch if you can help it... rendering order
        super.renderDirtBackground(0);
        // Logout error text
        TextRenderer var10001 = this.font;
        String var10002 = this.title.asFormattedString();
        int var10003 = this.width / 2;
        int var10004 = this.height / 2 - this.reasonHeight / 2;
        this.font.getClass();
        this.drawCenteredString(var10001, var10002, var10003, var10004 - 9 * 2, 11184810);
        int i = this.height / 2 - this.reasonHeight / 2;
        if (this.reasonFormatted != null) {
            for(Iterator var5 = this.reasonFormatted.iterator(); var5.hasNext(); i += 9) {
                String string = (String)var5.next();
                this.drawCenteredString(this.font, string, this.width / 2, i, 16777215);
                this.font.getClass();
            }
        }
        // Rendering Reconnect Text
        if(!reconnectButton) {
            TextRenderer var100001 = this.font;
            String var100002 = "Reconnecting feature on, attempting now...";
            int var100003 = this.width / 2;
            int var100004 = this.height / 2 - this.reasonHeight / 2 - 30;
            this.font.getClass();
            this.drawCenteredString(var100001, var100002, var100003, var100004 - 9 * 2, 11184810);
            int o = this.height / 2 - this.reasonHeight / 2;
            if (this.reasonFormatted != null) {
                for(Iterator var5 = this.reasonFormatted.iterator(); var5.hasNext(); o += 9) {
                    String string = (String)var5.next();
                    this.drawCenteredString(this.font, string, this.width / 2, o, 16777215);
                    this.font.getClass();
                }
            }
        }

        super.render(mouseX, mouseY, delta);
    }

}