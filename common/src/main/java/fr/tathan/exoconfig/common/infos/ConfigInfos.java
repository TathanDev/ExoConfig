package fr.tathan.exoconfig.common.infos;

import fr.tathan.exoconfig.common.post_validation.ValidationErrorHandler;
import fr.tathan.exoconfig.common.utils.Side;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigInfos {

    String modDisplayName();
    String name();

    Side side() default Side.COMMON;

    ValidationErrorHandler errorHandling() default ValidationErrorHandler.THROW_EXCEPTION;

    @Retention(RetentionPolicy.RUNTIME)
    @interface FileDescription {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface PossibleStringValues {
         String[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface PossibleIntValues {
        int[] value();
    }

}
