package fr.tathan.exoconfig.fabric.client;

import fr.tathan.exoconfig.ExoConfig;
import net.fabricmc.api.ClientModInitializer;

public final class ExoconfigFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ExoConfig.initClient();
    }
}
