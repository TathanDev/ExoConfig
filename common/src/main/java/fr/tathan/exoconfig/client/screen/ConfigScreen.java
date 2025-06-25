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


    public ConfigScreen(Screen parent, T configInstance) {
        super(Component.literal("Config"));
        this.parent = parent;
        this.configInstance = configInstance;
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
                String name = field.getName();

                if (field.isAnnotationPresent(ScreenInfos.InnerConfig.class)) {
                    configList.addBig(new StringWidget(Component.translatable("config.stellaris." + name).withStyle(ChatFormatting.BOLD), this.font));

                    addFields(field.getType().getFields(), field.get(object),  configList, recursionDepth + 1);
                    continue;
                }

                configList.addSmall(new StringWidget(Component.translatable("config.stellaris." + name), this.font), addTypeWidget(field, object, value, Component.translatable("config.stellaris." + name + ".desc")));

            } catch (Exception e) {
                e.printStackTrace();
            }
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
                    .apply(new ConfigWidgetRegistry.WidgetFactory(fieldName, configInstance, value, field, description));

        } else {
            SpriteIconButton unsupported = stellarisConfigButton(20);
            unsupported.setTooltip(Tooltip.create(Component.literal("Unsupported field type")));
            return unsupported;
        }
    }


    private void saveConfig() {
        Path configPath = PlatformHelper.getConfigPath().resolve(getConfigName() + ".json");

        try (Writer writer = Files.newBufferedWriter(configPath)) {
            ExoConfig.GSON.toJson(this.configInstance, this.configInstance.getClass(), writer);
        } catch (Exception e) {
            e.printStackTrace();
            playToast(Component.literal("Config Error"), Component.literal("Failed to save Stellaris config"));
        }
    }

    private SpriteIconButton stellarisConfigButton(int i) {
        return SpriteIconButton.builder(Component.literal("Config"), (button) -> {
            Util.getPlatform().openUri(PlatformHelper.getConfigPath().toUri());
        }, true).width(i).sprite(TEXTURE, 16, 16).build();
    }

    public String getConfigName() {
        ConfigInfos configInfos = Utils.getConfigInfos(configInstance.getClass());
        if (configInfos != null) {
            return configInfos.name();
        } else {
            return this.configInstance.getClass().getSimpleName();
        }
    }
}