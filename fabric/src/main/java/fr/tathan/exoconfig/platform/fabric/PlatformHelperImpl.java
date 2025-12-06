package fr.tathan.exoconfig.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class PlatformHelperImpl {
    public static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
