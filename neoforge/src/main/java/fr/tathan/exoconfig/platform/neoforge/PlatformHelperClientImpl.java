package fr.tathan.exoconfig.platform.neoforge;

import fr.tathan.exoconfig.client.screen.ConfigScreen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


public class PlatformHelperClientImpl {

    public static void registerConfigScreen(String modid, Object config) {
        ModContainer container = ModList.get().getModContainerById(modid).orElseThrow();

        container.registerExtensionPoint(IConfigScreenFactory.class, (cont, screen) -> new ConfigScreen<>(screen, config));
    }
}
