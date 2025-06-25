package fr.tathan.exoconfig.client.components;

import net.minecraft.client.gui.components.AbstractWidget;

import java.util.HashMap;
import java.util.Map;

public class CustomComponentRegistry {

    private final Map<Class, AbstractWidget> components = new HashMap<>();

    private static final CustomComponentRegistry INSTANCE = new CustomComponentRegistry();

    public static CustomComponentRegistry getInstance() {
        return INSTANCE;
    }

    private CustomComponentRegistry() {}


    public void registerComponent(Class<?> componentClass, AbstractWidget component) {
        if (components.containsKey(componentClass)) {
            throw new IllegalArgumentException("Component already registered for class: " + componentClass.getName());
        }
        registerComponent(componentClass, component, false);
    }

    public void registerComponent(Class<?> componentClass, AbstractWidget component, boolean replace) {
        if (replace && components.containsKey(componentClass)) {
            components.replace(componentClass, component);
        } else {
            components.put(componentClass, component);
        }
    }

    public boolean componentExistForClass(Class<?> entryClass) {
        return this.components.containsKey(entryClass);
    }



}
