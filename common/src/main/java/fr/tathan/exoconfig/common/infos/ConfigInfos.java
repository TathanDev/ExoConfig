package fr.tathan.exoconfig.common.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigInfos {

    String name();

    Side side() default Side.COMMON;


    @Retention(RetentionPolicy.RUNTIME)
    public @interface FileDescription {
        String value();
    }

    class ScreenInfo {

        @Retention(RetentionPolicy.RUNTIME)
        public @interface InnerConfig {
        }


    }
}
