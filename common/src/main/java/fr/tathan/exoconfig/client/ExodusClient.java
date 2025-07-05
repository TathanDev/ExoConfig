package fr.tathan.exoconfig.client;

import fr.tathan.exoconfig.client.components.ConfigWidgetRegistry;
import fr.tathan.exoconfig.client.components.RangedOption;
import fr.tathan.exoconfig.client.components.StateButton;
import fr.tathan.exoconfig.common.types.RangedNumber;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class ExodusClient {

    public static Class<?>[] editBoxClass = new Class[]{
            Integer.class,
            Long.class,
            Double.class,
            Float.class,
            String.class,
            Number.class
    };

    public static void init() {
        registerWidgetEntry();
    }

    public static void registerWidgetEntry() {

        ConfigWidgetRegistry.getInstance().registerComponent(Boolean.class,
                (factory) -> {
                    StateButton button = new StateButton(0, 0, 150, 20, Component.literal("StateButton"), (boolean) factory.defaultValue(), (b) -> {
                        try {
                            factory.field().set(factory.configInstance(), b);
                        } catch (IllegalAccessException e) {}
                    });

                    if(ScreenUtils.showTooltip(factory.field())) button.setTooltip(Tooltip.create(factory.description()));
                    return button;
                }
        );

        ConfigWidgetRegistry.getInstance().registerComponent(RangedNumber.class,
                (factory) -> {
                    RangedNumber number = (RangedNumber) factory.defaultValue();
                    RangedOption button = new RangedOption(0, 0, 150, 20, Component.literal("StateButton"),  number.getMin().doubleValue(), number.getMax().doubleValue(), number.getValue().doubleValue(), 1,  (b) -> {
                        try {
                            factory.field().set(factory.configInstance(), new RangedNumber(number.getMin(), number.getMax(), b.getCurrentValue()));
                        } catch (IllegalAccessException e) {}
                    });

                    if(ScreenUtils.showTooltip(factory.field())) button.setTooltip(Tooltip.create(factory.description()));
                    return button;
                }
        );

        for(Class<?> clazz : editBoxClass) ConfigWidgetRegistry.getInstance().registerComponent(clazz, ScreenUtils::numberAndStringWidget);

    }

}
