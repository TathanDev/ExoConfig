package fr.tathan.exoconfig.client.screen;

import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.client.components.ConfigList;
import fr.tathan.exoconfig.client.components.ConfigWidgetRegistry;
import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.infos.ScreenInfos;
import fr.tathan.exoconfig.platform.PlatformHelper;
import fr.tathan.exoconfig.common.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;


@Environment(EnvType.CLIENT)
public class ConfigScreen <T> extends Screen {

    public static final ResourceLocation TEXTURE = ResourceLocation.parse("exoconfig:textures/item/engine_fan");
    private final Screen parent;
    public final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    protected ConfigList configList;
    public T configInstance;

    private final ConfigInfos configInfos;


    public ConfigScreen(Screen parent, T configInstance) {
        super(Component.literal("Config"));
        this.parent = parent;
        this.configInstance = configInstance;
        this.configInfos = Utils.getConfigInfos(configInstance.getClass());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        this.addTitle();
        this.addFooter();

        configList = this.layout.addToContents(new ConfigList(this.minecraft, this.width, this));

        Class<T> clazz = (Class<T>) configInstance.getClass();

        addFields(clazz.getFields(), configInstance, configList, 0);

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
    }

    protected void addTitle() {
        this.layout.addTitleHeader(this.title, this.font);
    }

    protected void addFooter() {
        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (button) -> this.onClose()).width(200).build());
    }

    private void addFields(Field[] fields, Object object, ConfigList configList, int recursionDepth) {
        for (Field field : fields) {
            try {

                if(field.isAnnotationPresent(ScreenInfos.Hidden.class)) {
                    continue; // Skip hidden fields
                }

                Object value = field.get(object);

                if (field.isAnnotationPresent(ScreenInfos.InnerConfig.class)) {

                    configList.addBig(getTitleWidget(field, true));

                    addFields(field.getType().getFields(), field.get(object),  configList, recursionDepth + 1);

                    continue;
                }

                configList.addSmall(getTitleWidget(field, false), addTypeWidget(field, object, value, getWidgetDescription(field)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public StringWidget getTitleWidget(Field field, boolean title) {
        String name = field.getName();

        MutableComponent titleComponent = Component.translatable("config." + this.configInfos.name()  + "." + name);

        if(title) titleComponent.withStyle(ChatFormatting.BOLD);

        StringWidget stringWidget = new StringWidget(titleComponent, this.font);
        stringWidget.setTooltip(Tooltip.create(getWidgetDescription(field)));

        return stringWidget;
    }

    public Component getWidgetDescription(Field field) {
        if (field.isAnnotationPresent(ScreenInfos.Description.class)) {
            ScreenInfos.Description descriptionAnnotation = field.getAnnotation(ScreenInfos.Description.class);
            if (descriptionAnnotation.translate()) {
                return Component.translatable(descriptionAnnotation.value());
            }
            return Component.literal(descriptionAnnotation.value());
        } else {
            return Component.empty();
        }
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.configList != null) {
            this.configList.updateSize(this.width, this.layout);
        }
    }

    @Override
    public void onClose() {
        saveConfig();
        this.playToast(Component.literal("Config Saved"), Component.literal("The Stellaris config has been saved"));
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
    }

    @Override
    public void removed() {
        this.minecraft.options.save();
    }

    public void playToast(Component title, Component description) {
        this.minecraft.getToasts().addToast(new SystemToast(
                SystemToast.SystemToastId.PERIODIC_NOTIFICATION,
                title,
                description
        ));
    }

    private AbstractWidget addTypeWidget(Field field, Object configInstance, Object value, Component description) {
        String fieldName = field.getName();

        if(ConfigWidgetRegistry.getInstance().componentExistForClass(value.getClass())) {

            return ConfigWidgetRegistry.getInstance().getComponentForClass(value.getClass())
                    .apply(new ConfigWidgetRegistry.WidgetFactory(fieldName, configInstance, value, field, description, this));

        } else if (value instanceof Enum<?>) {
            return ConfigWidgetRegistry.getInstance().getComponentForClass(Enum.class)
                    .apply(new ConfigWidgetRegistry.WidgetFactory(fieldName, configInstance, value, field, description, this));
        } else {
            return ConfigWidgetRegistry.getInstance().getComponentForClass(Exception.class)
                    .apply(new ConfigWidgetRegistry.WidgetFactory(fieldName, configInstance, value, field, description, this));
        }
    }


    private void saveConfig() {
        Path configPath = PlatformHelper.getConfigPath().resolve(getConfigName() + ".json");

        try (Writer writer = Files.newBufferedWriter(configPath)) {
            ExoConfig.getGson().toJson(this.configInstance, this.configInstance.getClass(), writer);
        } catch (Exception e) {
            ExoConfig.LOG.error("Could not save config", e);
            this.playToast(Component.literal("Config Error"), Component.literal("Failed to save " + this.getConfigName() + " config"));
        }
    }


    public void openUri() {
        Util.getPlatform().openUri(PlatformHelper.getConfigPath().resolve(getConfigName() + ".json").toUri());
    }

    public String getConfigName() {
        if (this.configInfos != null) {
            return configInfos.name();
        } else {
            return this.configInstance.getClass().getSimpleName();
        }
    }
}