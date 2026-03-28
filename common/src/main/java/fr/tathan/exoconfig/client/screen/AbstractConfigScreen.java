package fr.tathan.exoconfig.client.screen;

import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractConfigScreen extends Screen {

    protected AbstractConfigScreen(Component component) {
        super(component);
    }


    public abstract HeaderAndFooterLayout getLayout();
}
