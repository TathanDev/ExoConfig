package fr.tathan.exoconfig.utils;

import fr.tathan.exoconfig.client.components.ConfigWidgetRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class Utils {

    public static  <T> ConfigInfos  getConfigInfos(Class<T> configClass) {

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

    public static EditBox numberAndStringWidget(ConfigWidgetRegistry.WidgetFactory factory) {
        EditBox editBox = new EditBox(Minecraft.getInstance().font, 100, 15, Component.literal(factory.fieldName()));
        editBox.setTooltip(Tooltip.create(factory.description()));
        editBox.setValue(factory.defaultValue().toString());

        editBox.setResponder(str -> {
            try {
                Object converted = Utils.convertValue(str, factory.field().getType());
                factory.field().set(factory.configInstance(), converted);
            } catch (Exception ignored) {}
        });
        return editBox;

    }


}
