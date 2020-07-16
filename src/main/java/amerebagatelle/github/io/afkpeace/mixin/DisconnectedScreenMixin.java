package amerebagatelle.github.io.afkpeace.mixin;

import amerebagatelle.github.io.afkpeace.AFKPeace;
import amerebagatelle.github.io.afkpeace.ConnectionManager;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {
    protected DisconnectedScreenMixin(Text title) {
        super(NarratorManager.EMPTY);
    }

    /**
     * Adds our button to the DisconnectedScreen
     */
    @Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        if (!Boolean.parseBoolean(SettingsManager.loadSetting("reconnectEnabled"))) {
            this.addButton(new ButtonWidget(width / 2 - 100, this.height - 30, 200, 20, new TranslatableText("reconnect"), (buttonWidget) -> ConnectionManager.INSTANCE.connectToServer(AFKPeace.currentServerEntry)));
        }
    }
}