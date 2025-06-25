package fr.tathan.exoconfig.common.infos;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ScreenInfos {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface InnerConfig {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Hidden {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface NoDescription {
    }
}
