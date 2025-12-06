package fr.tathan.exoconfig.neoforge;

import fr.tathan.exoconfig.ExoConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

@Mod(ExoConfig.MOD_ID)
public final class ExoconfigNeoForge {
    public ExoconfigNeoForge() {
        // Run our common setup.
        ExoConfig.init();
        NeoForge.EVENT_BUS.addListener(ExoconfigNeoForge::syncConfigs);

    }

    public static void syncConfigs(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            ExoConfig.syncConfigs(event.getPlayer(), true);
        }
    }

    @SubscribeEvent
    public static void onClientSetup(RegisterClientReloadListenersEvent event) {
        ExoConfig.initClient();
    }
}
