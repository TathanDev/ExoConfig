package fr.tathan.exoconfig.client.screen;

import fr.tathan.exoconfig.client.components.ConfigButton;
import fr.tathan.exoconfig.client.components.ConfigList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class MultiplesConfigScreen extends AbstractConfigScreen {

    public final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    public ConfigList configList;
    public final Object[] configs;

    public MultiplesConfigScreen(Screen parent, Object... configs) {
        super(Component.literal("Configs"));
        this.configs = configs;
    }

    @Override
    protected void init() {
        super.init();
        this.configList = new ConfigList(Minecraft.getInstance(), this.width, this);

        int i = 16;
        for(Object config : this.configs) {
            this.configList.addBig(new ConfigButton(0, 0, this.width - 150, 20, config, this));
        }

        this.layout.addToContents(this.configList);

        this.addTitle();
        this.addFooter();

        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();



    }

    protected void repositionElements() {
        this.layout.arrangeElements();

    }


    protected void addTitle() {
        this.layout.addTitleHeader(this.title, this.font);
    }

    protected void addFooter() {
        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (button) -> this.onClose()).width(200).build());
    }

    @Override
    public HeaderAndFooterLayout getLayout() {
        return this.layout;
    }
}
