package fr.tathan.exoconfig.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class PlatformClientHelper {
    @ExpectPlatform
    public static void registerConfigScreen(String modid, Object config) {
        throw new AssertionError();
    }

}
