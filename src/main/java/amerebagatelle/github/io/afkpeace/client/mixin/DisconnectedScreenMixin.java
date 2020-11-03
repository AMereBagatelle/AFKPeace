package amerebagatelle.github.io.afkpeace.client.mixin;

import amerebagatelle.github.io.afkpeace.client.AFKPeaceClient;
import amerebagatelle.github.io.afkpeace.client.ConnectionManager;
import amerebagatelle.github.io.afkpeace.client.settings.SettingsManager;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
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

    protected DisconnectedScreenMixin(Text title) {
        super(NarratorManager.EMPTY);
    }

    /**
     * Adds our button to the DisconnectedScreen
     */
    @Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        timeOfDisconnect = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (!Boolean.parseBoolean(SettingsManager.loadSetting("reconnectEnabled")) && AFKPeaceClient.currentServerEntry != null) {
            this.addButton(new ButtonWidget(width / 2 - 100, this.height - 30, 200, 20, new TranslatableText("reconnect"), (buttonWidget) -> ConnectionManager.INSTANCE.connectToServer(AFKPeaceClient.currentServerEntry)));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        drawCenteredString(matrices, textRenderer, I18n.translate("afkpeace.disconnectscreen.time", timeOfDisconnect), width / 2, height - 50, 16777215);
    }
}