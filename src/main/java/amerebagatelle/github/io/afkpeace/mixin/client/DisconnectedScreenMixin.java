package amerebagatelle.github.io.afkpeace.mixin.client;

import amerebagatelle.github.io.afkpeace.AFKPeaceClient;
import amerebagatelle.github.io.afkpeace.ConnectionManagerKt;
import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Adds a button for reconnecting to the server
 */
@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {
    private String timeOfDisconnect;

    protected DisconnectedScreenMixin() {
        super(Text.empty());
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        timeOfDisconnect = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (!AFKPeaceConfigManager.RECONNECT_ENABLED.value() && AFKPeaceClient.currentServerEntry != null) {
            var button = new ButtonWidget.Builder(Text.translatable("afkpeace.reconnect"), (buttonWidget) -> ConnectionManagerKt.connectToServer(AFKPeaceClient.currentServerEntry))
                    .position(width / 2 - 100, this.height - 30)
                    .size(200, 20)
                    .build();
            this.addDrawableChild(button);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (AFKPeaceClient.disabled)
            graphics.drawCenteredShadowedText(textRenderer, I18n.translate("afkpeace.disconnectscreen.disabled"), width / 2, height - 70, 16777215);
        graphics.drawCenteredShadowedText(textRenderer, I18n.translate("afkpeace.disconnectscreen.time", timeOfDisconnect), width / 2, height - 50, 16777215);
    }
}