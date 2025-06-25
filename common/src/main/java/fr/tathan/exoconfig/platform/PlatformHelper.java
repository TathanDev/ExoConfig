package fr.tathan.exoconfig.platform;


import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class PlatformHelper {

    @ExpectPlatform
    public static Path getConfigPath() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerConfigScreen(String modid, Object config) {
        throw new AssertionError();
    }
}
