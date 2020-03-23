package net.bagatelle.afkpeace.settings;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.bagatelle.afkpeace.AFKPeace;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public String getModId() {
        return AFKPeace.modId;
    }
    
}