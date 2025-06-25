package fr.tathan.exoconfig.neoforge;

import fr.tathan.exoconfig.Exoconfig;
import net.neoforged.fml.common.Mod;

@Mod(Exoconfig.MOD_ID)
public final class ExoconfigNeoForge {
    public ExoconfigNeoForge() {
        // Run our common setup.
        Exoconfig.init();
    }
}
