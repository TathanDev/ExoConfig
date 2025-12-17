package fr.tathan.exoconfig.common;

import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.infos.ScreenInfos;
import fr.tathan.exoconfig.common.types.RangedNumber;
import fr.tathan.exoconfig.common.utils.Side;
import net.minecraft.resources.Identifier;

@ConfigInfos(modDisplayName = "ExoConfig Test", name = "test-config")
public class Config {
    public String name = "TestConfig";

    public RangedNumber rangedNumber = new RangedNumber(0, 100, 50);

    public Identifier location = Identifier.parse("minecraft:example");

    public Side side = Side.COMMON;

    public boolean debug = false;

    @ScreenInfos.Hidden
    public boolean secretEntry = true;

    @ScreenInfos.InnerConfig
    public InnerConfig innerConfig = new InnerConfig();


    public static class InnerConfig {

        public String innerValue = "Inner Value";
        public int innerNumber = 42;
    }
}
