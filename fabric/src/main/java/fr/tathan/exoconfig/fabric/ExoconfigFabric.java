package fr.tathan.exoconfig.fabric;

import fr.tathan.exoconfig.ExoConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class ExoconfigFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExoConfig.init();
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(ExoConfig::syncConfigs);

    }
}
