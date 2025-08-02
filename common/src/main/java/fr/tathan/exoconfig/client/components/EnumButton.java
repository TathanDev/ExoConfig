package fr.tathan.exoconfig.client.components;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.common.post_validation.ValidationErrorHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public class EnumButton extends AbstractButton {
    private static final WidgetSprites SPRITES = new WidgetSprites(ResourceLocation.withDefaultNamespace("widget/button"), ResourceLocation.withDefaultNamespace("widget/button_disabled"), ResourceLocation.withDefaultNamespace("widget/button_highlighted"));

    public String[] label;
    public Consumer<Integer> onPress;
    public Class<? extends Enum<?>> enumValue;
    public int index = 0;
    
    public EnumButton(int x, int y, int width, int height, Component message, Consumer<Integer> onPress, Class<? extends Enum<?>> enumValue, Object defaultValue) {
        super(x, y, width, height, message);
        this.onPress = onPress;
        this.label = EnumButton.getEnumValues(enumValue);
        this.enumValue = enumValue;
        setIndex(defaultValue);
    }


    public void nextValue() {
        this.index++;
        if (this.index >= label.length) {
            this.index = 0;
        }
        onPress.accept(this.index);
    }

    @Override
    public @NotNull Component getMessage() {
        return Component.literal(label[index]);
    }

    @Override
    public void onPress() {
        this.nextValue();
        onPress.accept(this.index);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());

        this.renderString(guiGraphics, minecraft.font, 16777215 );

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public void setIndex(Object defaultValue) {
        if (enumValue.isInstance(defaultValue)) {
            Enum<?>[] enumConstants = enumValue.getEnumConstants();
            for (int i = 0; i < enumConstants.length; i++) {
                if (enumConstants[i].equals(defaultValue)) {
                    this.index = i;
                    break;
                }
            }
        }
    }

    public static Enum<?> getEnumValue(Class<? extends Enum<?>> enumClass, int index) {
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        if (index < 0 || index >= enumConstants.length) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for enum " + enumClass.getName());
        }
        return enumConstants[index];
    }
    
    public static String[] getEnumValues(Class<? extends Enum<?>> enumClass) {
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        String[] values = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            values[i] = enumConstants[i].name();
        }
        return values;
    }
}
