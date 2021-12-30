package amerebagatelle.github.io.afkpeace.mixin.client;

import amerebagatelle.github.io.afkpeace.AFKPeaceClient;
import amerebagatelle.github.io.afkpeace.ConnectionManager;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {
    private String timeOfDisconnect;

    protected DisconnectedScreenMixin() {
        super(NarratorManager.EMPTY);
    }

    /**
     * Adds our button to the DisconnectedScreen
     */
    @Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        timeOfDisconnect = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (!AFKPeaceClient.CONFIG.reconnectEnabled && AFKPeaceClient.currentServerEntry != null) {
            this.addDrawableChild(new ButtonWidget(width / 2 - 100, this.height - 30, 200, 20, new TranslatableText("afkpeace.reconnect"), (buttonWidget) -> ConnectionManager.INSTANCE.connectToServer(AFKPeaceClient.currentServerEntry)));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (AFKPeaceClient.disabled)
            drawCenteredText(matrices, textRenderer, I18n.translate("afkpeace.disconnectscreen.disabled"), width / 2, height - 70, 16777215);
        drawCenteredText(matrices, textRenderer, I18n.translate("afkpeace.disconnectscreen.time", timeOfDisconnect), width / 2, height - 50, 16777215);
    }
}