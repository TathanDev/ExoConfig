package fr.tathan.exoconfig.utils;

public class ConfigHolder<T> {

    private T config;
    private ConfigInfos infos;
    private T configInstance;

    public ConfigHolder(T config, ConfigInfos infos, T configInstance) {
        this.config = config;
        this.infos = infos;
        this.configInstance = configInstance;
    }

    public T getConfig() {
        return config;
    }

    public ConfigInfos getName() {
        return infos;
    }

    public T getConfigInstance() {
        return configInstance;
    }

    public void setConfig(Object config) {
        this.config = (T) config;
    }
}
