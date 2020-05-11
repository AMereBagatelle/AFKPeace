package amerebagatelle.github.io.afkpeace.miscellaneous;

import java.util.Iterator;
import java.util.List;

import amerebagatelle.github.io.afkpeace.AFKPeace;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class DisconnectRetryScreen extends Screen {

    public DisconnectRetryScreen(Screen parent, String title, Text reason, ServerInfo serverInfo) {
        super(new TranslatableText(title));
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {

    }
    
    public void render(int mouseX, int mouseY, float delta) {

    }

}