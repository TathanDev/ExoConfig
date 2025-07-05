package fr.tathan.exoconfig.common;

import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.infos.ScreenInfos;
import fr.tathan.exoconfig.common.types.RangedNumber;

@ConfigInfos(name = "test-config")
public class Config {

    @ScreenInfos.Description(value = "This is a test configuration file.", translate = false)
    public String name = "TestConfig";

    public RangedNumber rangedNumber = new RangedNumber(0, 100, 50);


    @ScreenInfos.Hidden
    public boolean secretEntry = true;

    @ScreenInfos.InnerConfig
    public InnerConfig innerConfig = new InnerConfig();


    public static class InnerConfig {

        public String innerValue = "Inner Value";
        public int innerNumber = 42;
    }
}
