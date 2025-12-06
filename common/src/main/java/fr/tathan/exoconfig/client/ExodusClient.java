package fr.tathan.exoconfig.client;

import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.client.components.ConfigWidgetRegistry;
import fr.tathan.exoconfig.client.components.EnumButton;
import fr.tathan.exoconfig.client.components.RangedOption;
import fr.tathan.exoconfig.client.components.StateButton;
import fr.tathan.exoconfig.common.types.RangedNumber;
import fr.tathan.exoconfig.platform.PlatformClientHelper;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

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
        PlatformClientHelper.registerConfigScreen(ExoConfig.MOD_ID, ExoConfig.EXO_CONFIG);
        registerWidgetEntry();
    }

    @SuppressWarnings("unchecked")
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

        ConfigWidgetRegistry.getInstance().registerComponent(Enum.class,
                (factory) -> {
                    EnumButton button = new EnumButton(0, 0, 150, 20, Component.literal("EnumButton"), (index) -> {
                        try {
                            factory.field().set(factory.configInstance(), EnumButton.getEnumValue( (Class<? extends Enum<?>>) factory.defaultValue().getClass(), index));
                        } catch (IllegalAccessException e) {}
                    }, (Class<? extends Enum<?>>) factory.defaultValue().getClass(), factory.defaultValue());

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

        ConfigWidgetRegistry.getInstance().registerComponent(ResourceLocation.class, ScreenUtils::resourceLocationWidget);

        ConfigWidgetRegistry.getInstance().registerComponent(Exception.class, (factory) -> Button.builder(Component.literal("Open the config to edit"), (b) -> factory.configScreen().openUri()).build());

        for(Class<?> clazz : editBoxClass) ConfigWidgetRegistry.getInstance().registerComponent(clazz, ScreenUtils::numberAndStringWidget);

    }

}
