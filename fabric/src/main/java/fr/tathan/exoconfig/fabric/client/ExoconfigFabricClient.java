package fr.tathan.exoconfig.fabric.client;

import fr.tathan.exoconfig.client.ExodusClient;
import net.fabricmc.api.ClientModInitializer;

public final class ExoconfigFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ExodusClient.init();
    }
}
