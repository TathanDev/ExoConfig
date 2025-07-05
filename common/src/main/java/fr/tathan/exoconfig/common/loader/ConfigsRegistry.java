package fr.tathan.exoconfig.common.loader;

import fr.tathan.exoconfig.common.utils.ConfigHolder;
import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.post_validation.PostValidationUtils;
import fr.tathan.exoconfig.common.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ConfigsRegistry {

    private static final ConfigsRegistry INSTANCE = new ConfigsRegistry();

    public static ConfigsRegistry getInstance() {
        return INSTANCE;
    }

    private final Map<String, ConfigHolder<?>> configs = new HashMap<>();

    private ConfigsRegistry() {}


    public <T> T registerConfig(T config, T configInstance) {
        ConfigInfos configInfos = Utils.getConfigInfos(config.getClass());
        if (configInfos == null) {
            throw new IllegalArgumentException("Config class must be annotated with @ConfigInfos");
        }
        if (configs.containsKey(configInfos.name())) {
            configs.replace(configInfos.name(), new ConfigHolder<T>(config, configInfos, configInstance));
        } else {
            configs.put(configInfos.name(), new ConfigHolder<T>(config, configInfos, configInstance));
        }
        T loadedConfig = ConfigLoader.loadOrGenerateDefaults(config);
        PostValidationUtils.postValidate(loadedConfig);
        return loadedConfig;
    }


    public ConfigHolder<?> getConfig(String name) {
         return configs.get(name);
    }

    public Map<String, ConfigHolder<?>> getConfigs() {
        return configs;
    }
}
