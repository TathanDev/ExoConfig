package fr.tathan.exoconfig.common.post_validation;

import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.types.ConfigType;

import java.lang.reflect.Field;

public class PostValidationUtils {

    /**
     * This method is called after the configuration has been loaded and validated.
     * It can be used to perform any additional checks or modifications to the configuration.
     */
    @SuppressWarnings("unchecked")
    public static <T> void postValidate(T configInstance)  {
        Class<T> clazz = (Class<T>) configInstance.getClass();
        Field[] fields = clazz.getFields();

        if(configInstance instanceof PostValidation postValidation) {
            postValidation.postValidation();
        }

        for (Field field : fields) {
            try {

                Object value = field.get(configInstance);

                if (value instanceof ConfigType<?> configType) {
                    configType.postValidation();
                }

                if(value instanceof String) {
                    if (!checkPossibleStringValues(field, value)) {
                        handleStringException(configInstance, field.getAnnotation(ConfigInfos.PossibleStringValues.class), field, value);
                    }
                } else if (value instanceof Integer) {
                    if (!checkPossibleIntValues(field, value)) {
                        handleIntException(configInstance, field.getAnnotation(ConfigInfos.PossibleIntValues.class), field, value);
                    }
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access field: " + field.getName(), e);
            }
        }
    }

    public static boolean checkPossibleStringValues(Field field, Object value) {
        if (field.isAnnotationPresent(ConfigInfos.PossibleStringValues.class)) {
            ConfigInfos.PossibleStringValues possibleValues = field.getAnnotation(ConfigInfos.PossibleStringValues.class);
            for (String possibleValue : possibleValues.values()) {
                if (possibleValue.equals(value)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean checkPossibleIntValues(Field field, Object value) {
        if (field.isAnnotationPresent(ConfigInfos.PossibleIntValues.class)) {
            ConfigInfos.PossibleIntValues possibleValues = field.getAnnotation(ConfigInfos.PossibleIntValues.class);
            for (Integer possibleValue : possibleValues.values()) {
                if (possibleValue.equals(value)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static <T> void handleStringException(Object configInstance, ConfigInfos.PossibleStringValues stringValues, Field field, Object value) {
        ConfigInfos infos = configInstance.getClass().getAnnotation(ConfigInfos.class);
        switch (infos.errorHandling()) {
            case THROW_EXCEPTION -> throw new IllegalArgumentException("Invalid string value for field: " + field.getName() + ", value: " + value);
            case FIRST_VALUE -> {
                try {
                    field.set(configInstance, stringValues.values()[0]);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to set field: " + field.getName(), e);
                }
            }
        }
    }

    public static <T> void handleIntException(Object configInstance, ConfigInfos.PossibleIntValues intValues, Field field, Object value) {
        ConfigInfos infos = configInstance.getClass().getAnnotation(ConfigInfos.class);
        switch (infos.errorHandling()) {
            case THROW_EXCEPTION -> throw new IllegalArgumentException("Invalid string value for field: " + field.getName() + ", value: " + value);
            case FIRST_VALUE -> {
                try {
                    field.set(configInstance, intValues.values()[0]);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to set field: " + field.getName(), e);
                }
            }
        }
    }


}
