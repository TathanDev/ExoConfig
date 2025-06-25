package fr.tathan.exoconfig.platform.fabric;

import com.google.common.collect.Maps;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.client.screen.ConfigScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;

import java.nio.file.Path;
import java.util.Map;

public class PlatformHelperImpl {

    public static final Map<String, ConfigScreenFactory<?>> CONFIG_SCREENS = Maps.newHashMap();


    public static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static void registerConfigScreen(String modid, Object config) {
        ExoConfig.LOG.warn("Automatic config screen registration is not supported on Fabric. Please register your config screen manually in your mod's initialization code.");
    }
}
