package fr.tathan.exoconfig.common.test;

import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.infos.ScreenInfos;

@ConfigInfos(name = "test-config")
public class Config {

    @ScreenInfos.NoDescription
    public String name = "TestConfig";

    @ConfigInfos.FileDescription("This is a test config file for ExoConfig.")
    public boolean enabled = true;

}
