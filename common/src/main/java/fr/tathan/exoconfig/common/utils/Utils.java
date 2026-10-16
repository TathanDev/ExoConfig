package fr.tathan.exoconfig.common.utils;

import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.infos.ScreenInfos;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Utils {

    public static <T> ConfigInfos getConfigInfos(Class<T> configClass) {

        if (configClass.isAnnotationPresent(ConfigInfos.class)) {
            return configClass.getAnnotation(ConfigInfos.class);
        }
        return null;
    }

    public static Object convertValue(String str, Class<?> type) {
        return switch (type.getSimpleName()) {
            case "int", "Integer" -> Integer.parseInt(str);
            case "long", "Long" -> Long.parseLong(str);
            case "double", "Double" -> Double.parseDouble(str);
            case "float", "Float" -> Float.parseFloat(str);
            default -> str;
        };
    }

    public static void copyFields(Object source, Object target) throws IllegalAccessException {
        for (Class<?> clazz = target.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;

                field.setAccessible(true);
                Object value = field.get(source);
                Object current = field.get(target);

                if (field.isAnnotationPresent(ScreenInfos.InnerConfig.class) && value != null && current != null) {
                    copyFields(value, current);
                } else {
                    field.set(target, value);
                }
            }
        }
    }
}
