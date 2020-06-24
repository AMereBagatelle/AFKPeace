package amerebagatelle.github.io.afkpeace.mixin;

import amerebagatelle.github.io.afkpeace.AFKPeace;
import amerebagatelle.github.io.afkpeace.ConnectionManager;
import amerebagatelle.github.io.afkpeace.settings.SettingsManager;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {
    protected DisconnectedScreenMixin(Text title) {
        super(NarratorManager.EMPTY);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        if (!Boolean.parseBoolean(SettingsManager.loadSetting("reconnectEnabled"))) { // TODO string literal
            this.addButton(new ButtonWidget(width / 2 - 100, this.height / 2 + 40, 200, 20, "Reconnect", (buttonWidget) -> ConnectionManager.INSTANCE.connectToServer(AFKPeace.currentServerEntry)));
        }
    }
}