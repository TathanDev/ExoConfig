package fr.tathan.exoconfig.client.components;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class RangedOption extends AbstractSliderButton {
    public final double minValue;
    public final double maxValue;
    public final double step;

    public Consumer<RangedOption> onValueChange;

    public RangedOption(int x, int y, int width, int height, Component message, double minValue, double maxValue, double initialValue, double step, Consumer<RangedOption> onValueChange) {
        super(x, y, width, height, message, RangedOption.normalizeValue(initialValue, minValue, maxValue));
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.step = step;
        this.onValueChange = onValueChange;
        this.updateMessage();
    }


    protected static double normalizeValue(double value, double min, double max) {
        return Mth.clamp((value - min) / (max - min), 0.0, 1.0);
    }


    protected static double denormalizeValue(double normalizedValue, double min, double max) {
        return min + normalizedValue * (max - min);
    }


    public void setValue(double rawValue) {
        this.value = RangedOption.normalizeValue(rawValue, this.minValue, this.maxValue);
        this.applyValue();
        this.updateMessage();
    }


    public double getCurrentValue() {
        double denormalizedValue = RangedOption.denormalizeValue(
                this.value,
                this.minValue,
                this.maxValue
        );
        return this.snapToStep(denormalizedValue);
    }


    protected double snapToStep(double value) {
        if (this.step == 0.0) {
            return value;
        }
        return Math.round(value / this.step) * this.step;
    }

    @Override
    public void setValueFromMouse(double mouseX) {
        super.setValueFromMouse(mouseX); // This updates `this.value` (0-1 normalized)
        this.value = normalizeValue(snapToStep(denormalizeValue(this.value, minValue, maxValue)), minValue, maxValue);
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.canChangeValue) {
            boolean isLeft = keyCode == 263; // GLFW.GLFW_KEY_LEFT
            boolean isRight = keyCode == 262; // GLFW.GLFW_KEY_RIGHT

            if (isLeft || isRight) {
                double currentValue = getCurrentValue();
                double newValue = currentValue + (isLeft ? -this.step : this.step);
                this.setValue(newValue);
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    // Abstract methods to be implemented by concrete classes using this slider
    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal("" + this.getCurrentValue()));
    }

    @Override
    protected void applyValue() {
        this.onValueChange.accept(this);
    }
}
