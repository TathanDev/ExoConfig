package fr.tathan.exoconfig.client.components;

import fr.tathan.exoconfig.client.screen.ConfigScreen;
import fr.tathan.exoconfig.common.infos.ConfigInfos;
import fr.tathan.exoconfig.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigButton<T> extends AbstractButton {

    public T configInstance;
    public ConfigInfos configInfos;
    Screen screen;
    public ConfigButton(int x, int y, int width, int height, T configInstance, Screen screen) {
        super(x, y, width, height, Component.empty());

        this.configInstance = configInstance;
        this.configInfos = Utils.getConfigInfos(configInstance.getClass());
        this.screen = screen;
    }

    @Override
    public void onPress() {
        Minecraft.getInstance().setScreen(new ConfigScreen<>(screen, configInstance));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public Component getMessage() {
        return Component.literal(this.configInfos.modDisplayName());
    }
}
