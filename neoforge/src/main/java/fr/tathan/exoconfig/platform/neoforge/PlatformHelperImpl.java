package fr.tathan.exoconfig.platform.neoforge;

import fr.tathan.exoconfig.client.screen.ConfigScreen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.nio.file.Path;

public class PlatformHelperImpl {

    public static Path getConfigPath() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static void registerConfigScreen(String modid, Object config) {
        ModContainer container = ModList.get().getModContainerById(modid).orElseThrow();

        container.registerExtensionPoint(IConfigScreenFactory.class, (cont, screen) -> {
            return new ConfigScreen<>(screen, config);
        });

    }
}
