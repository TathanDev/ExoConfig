package fr.tathan.exoconfig.fabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import fr.tathan.exoconfig.client.screen.ConfigScreen;
import fr.tathan.exoconfig.client.screen.MultiplesConfigScreen;
import fr.tathan.exoconfig.platform.fabric.PlatformClientHelperImpl;

import java.util.HashMap;
import java.util.Map;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> factories = new HashMap<>();

        PlatformClientHelperImpl.getRegisteredConfigScreens().forEach((modid, configs) -> {
            if (configs.length == 1) {
                factories.put(modid, previous -> new ConfigScreen<>(previous, configs[0]));
            } else {
                factories.put(modid, previous -> new MultiplesConfigScreen(previous, configs));
            }
        });

        return factories;
    }
}
