package fr.tathan.exoconfig.loader;

import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.utils.ConfigInfos;
import fr.tathan.exoconfig.platform.PlatformHelper;
import fr.tathan.exoconfig.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class ConfigLoader {

    @SuppressWarnings("unchecked")
    public static <T> T loadOrGenerateDefaults(Object config) {
        Path systemsFile = PlatformHelper.getConfigPath();

        Class<T> configClass = (Class<T>) config.getClass();

        ConfigInfos configInfos = Utils.getConfigInfos(configClass);
        if(configInfos != null) {
            systemsFile = systemsFile.resolve(configInfos.name() + ".json");
        } else {
            systemsFile = PlatformHelper.getConfigPath().resolve(configClass.getName() + ".json");
        }

            try {
                BufferedReader reader = Files.newBufferedReader(systemsFile);
                T newConfig = ExoConfig.GSON.fromJson(reader, configClass);

                Writer writer = new FileWriter(systemsFile.toFile());
                ExoConfig.GSON.toJson(newConfig, writer);
                writer.close();

                return newConfig;

            } catch (Exception e) {

                if (!(e instanceof NoSuchFileException))
                    e.printStackTrace();

                try {
                    File folder = systemsFile.toFile().getParentFile();
                    if (!folder.exists())
                        folder.mkdirs();

                    Writer writer = new FileWriter(systemsFile.toFile());
                    ExoConfig.GSON.toJson(config, writer);
                    writer.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }


        return (T) config;
    }



}
