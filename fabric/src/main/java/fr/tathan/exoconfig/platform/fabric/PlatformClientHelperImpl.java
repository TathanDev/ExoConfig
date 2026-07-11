package fr.tathan.exoconfig.platform.fabric;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlatformClientHelperImpl {

    private static final Map<String, Object[]> CONFIG_SCREENS = new LinkedHashMap<>();

    public static void registerConfigScreen(String modid, Object config) {
        CONFIG_SCREENS.put(modid, new Object[]{config});
    }

    public static void registerConfigScreens(String modid, Object... configs) {
        CONFIG_SCREENS.put(modid, configs);
    }

    public static Map<String, Object[]> getRegisteredConfigScreens() {
        return CONFIG_SCREENS;
    }

}
