package fr.tathan.exoconfig.neoforge.network;

import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.common.network.SyncConfigPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ExoConfig.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkRegistry {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                SyncConfigPacket.TYPE,
                SyncConfigPacket.STREAM_CODEC,
                (packet, ctx) -> SyncConfigPacket.handle(packet)
        );
    }
}
