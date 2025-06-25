package fr.tathan.exoconfig.client.components;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConfigWidgetRegistry {

    private final Map<Class, Function<WidgetFactory, AbstractWidget>> components = new HashMap<>();

    private static final ConfigWidgetRegistry INSTANCE = new ConfigWidgetRegistry();

    public static ConfigWidgetRegistry getInstance() {
        return INSTANCE;
    }

    private ConfigWidgetRegistry() {}


    public void registerComponent(Class<?> componentClass, Function<WidgetFactory, AbstractWidget> component) {
        if (components.containsKey(componentClass)) {
            throw new IllegalArgumentException("Component already registered for class: " + componentClass.getName());
        }
        registerComponent(componentClass, component, false);
    }

    public void registerComponent(Class<?> componentClass, Function<WidgetFactory, AbstractWidget> component, boolean replace) {
        if (replace && components.containsKey(componentClass)) {
            components.replace(componentClass, component);
        } else {
            components.put(componentClass, component);
        }
    }

    public boolean componentExistForClass(Class<?> entryClass) {
        return this.components.containsKey(entryClass);
    }

    public Function<WidgetFactory, AbstractWidget> getComponentForClass(Class<?> entryClass) {
        return this.components.get(entryClass);
    }

    public record WidgetFactory(String fieldName, Object configInstance, Object defaultValue, Field field, Component description) {

    }


}
