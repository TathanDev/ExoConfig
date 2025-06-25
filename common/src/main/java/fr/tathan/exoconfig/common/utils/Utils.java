package fr.tathan.exoconfig.common.utils;

import fr.tathan.exoconfig.common.infos.ConfigInfos;

public class Utils {

    public static  <T> ConfigInfos getConfigInfos(Class<T> configClass) {

        if (configClass.isAnnotationPresent(ConfigInfos.class)) {
            ConfigInfos configInfos = configClass.getAnnotation(ConfigInfos.class);
            return configInfos;
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



}
