package fr.tathan.exoconfig.client;

import fr.tathan.exoconfig.client.components.ConfigWidgetRegistry;
import fr.tathan.exoconfig.common.infos.ScreenInfos;
import fr.tathan.exoconfig.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;

public class ScreenUtils {

    public static boolean showTooltip(Field field) {
        return field.isAnnotationPresent(ScreenInfos.Description.class); // This can be modified to return false if you want to disable tooltips
    }

    public static EditBox numberAndStringWidget(ConfigWidgetRegistry.WidgetFactory factory) {
        EditBox editBox = new EditBox(Minecraft.getInstance().font, 150, 20, Component.literal(factory.fieldName()));

        if(ScreenUtils.showTooltip(factory.field())) {
            editBox.setTooltip(Tooltip.create(factory.description()));
        }
        editBox.setValue(factory.defaultValue().toString());

        editBox.setResponder(str -> {
            try {
                Object converted = Utils.convertValue(str, factory.field().getType());
                factory.field().set(factory.configInstance(), converted);
            } catch (Exception ignored) {}
        });
        return editBox;

    }

    public static EditBox resourceLocationWidget(ConfigWidgetRegistry.WidgetFactory factory) {
        EditBox editBox = new EditBox(Minecraft.getInstance().font, 150, 20, Component.literal(factory.fieldName()));

        if(ScreenUtils.showTooltip(factory.field())) {
            editBox.setTooltip(Tooltip.create(factory.description()));
        }
        editBox.setValue(factory.defaultValue().toString());

        editBox.setResponder(str -> {
            try {
                ResourceLocation location = ResourceLocation.parse(str);
                factory.field().set(factory.configInstance(), location);
            } catch (Exception ignored) {}
        });
        return editBox;

    }


}
