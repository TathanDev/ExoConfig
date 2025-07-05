package fr.tathan.exoconfig.common.utils;

import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.types.ConfigType;

import java.lang.reflect.Field;

public class PostValidation {

    /**
     * This method is called after the configuration has been loaded and validated.
     * It can be used to perform any additional checks or modifications to the configuration.
     */
    @SuppressWarnings("unchecked")
    public static <T> void postValidate(T configInstance)  {
        Class<T> clazz = (Class<T>) configInstance.getClass();
        Field[] fields = clazz.getFields();
        
        for (Field field : fields) {
            try {

                Object value = field.get(configInstance);

                if (value instanceof ConfigType<?> configType) {
                    configType.postValidation();
                }

                if( value instanceof String) {
                    if (!checkPossibleStringValues(field, value)) {
                        throw new IllegalArgumentException("Invalid string value for field: " + field.getName() + ", value: " + value);
                    }
                } else if (value instanceof Integer) {
                    if (!checkPossibleIntValues(field, value)) {
                        throw new IllegalArgumentException("Invalid integer value for field: " + field.getName() + ", value: " + value);
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
        }
        return false;
    }

    public static boolean checkPossibleIntValues(Field field, Object value) {
        if (field.isAnnotationPresent(ConfigInfos.PossibleIntValues.class)) {
            ConfigInfos.PossibleIntValues possibleValues = field.getAnnotation(ConfigInfos.PossibleIntValues.class);
            for (Integer possibleValue : possibleValues.values()) {
                if (possibleValue.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

}
