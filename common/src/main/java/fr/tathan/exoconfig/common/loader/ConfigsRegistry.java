package fr.tathan.exoconfig.loader;

import fr.tathan.exoconfig.utils.ConfigHolder;
import fr.tathan.exoconfig.utils.ConfigInfos;
import fr.tathan.exoconfig.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ConfigsRegistry {

    private static final ConfigsRegistry INSTANCE = new ConfigsRegistry();

    public static ConfigsRegistry getInstance() {
        return INSTANCE;
    }

    private final Map<String, ConfigHolder> configs = new HashMap<>();

    private ConfigsRegistry() {}


    public <T> T registerConfig(T config, T configInstance) {
        ConfigInfos configInfos = Utils.getConfigInfos(config.getClass());
        if (configInfos == null) {
            throw new IllegalArgumentException("Config class must be annotated with @ConfigInfos");
        }
        configs.put(configInfos.name(), new ConfigHolder<T>(config, configInfos, configInstance));
        return ConfigLoader.loadOrGenerateDefaults(config);
    }


    public ConfigHolder<?> getConfig(String name) {
         return configs.get(name);
    }
}
