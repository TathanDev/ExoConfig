package fr.tathan.exoconfig.common;

import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.infos.ScreenInfos;

@ConfigInfos(name = "test-config")
public class Config {

    @ScreenInfos.NoDescription
    public String name = "TestConfig";

    @ScreenInfos.Hidden
    public boolean secretEntry = true;

    @ScreenInfos.InnerConfig
    public InnerConfig innerConfig = new InnerConfig();

    public static class InnerConfig {

        public String innerValue = "Inner Value";
        public int innerNumber = 42;
    }
}
