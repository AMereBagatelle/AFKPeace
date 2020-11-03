package amerebagatelle.github.io.afkpeace.client.modmenu;

import amerebagatelle.github.io.afkpeace.client.gui.AFKPeaceConfig;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuEntrypointImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return AFKPeaceConfig::new;
    }
}
