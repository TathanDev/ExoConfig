package fr.tathan.exoconfig.fabric;

import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.fabric.network.NetworkRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class ExoconfigFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NetworkRegistry.init();
        ExoConfig.init();
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(ExoConfig::syncConfigs);

    }
}
