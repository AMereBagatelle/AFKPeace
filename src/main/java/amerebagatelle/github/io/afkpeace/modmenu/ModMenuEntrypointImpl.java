package amerebagatelle.github.io.afkpeace.modmenu;

import amerebagatelle.github.io.afkpeace.gui.AFKPeaceConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuEntrypointImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return AFKPeaceConfig::new;
    }
}
