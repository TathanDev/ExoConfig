package fr.tathan.exoconfig.fabric.client;

import fr.tathan.exoconfig.fabric.network.NetworkRegistry;
import net.fabricmc.api.ClientModInitializer;

public final class ExoconfigFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkRegistry.initClient();
    }
}
