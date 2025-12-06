package fr.tathan.exoconfig.neoforge;

import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.client.ExodusClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
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

    @EventBusSubscriber(modid = ExoConfig.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ExodusClient.init();
        }
    }
}
