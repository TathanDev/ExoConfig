package fr.tathan.exoconfig.platform.fabric;

import fr.tathan.exoconfig.ExoConfig;

public class PlatformClientHelperImpl {

    public static void registerConfigScreen(String modid, Object config) {
        ExoConfig.LOG.warn("Automatic config screen registration is not supported on Fabric. Please register your config screen manually in your mod's initialization code.");
    }

}
