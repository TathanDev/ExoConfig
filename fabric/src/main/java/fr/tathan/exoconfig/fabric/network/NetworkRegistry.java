package fr.tathan.exoconfig.fabric.network;

import fr.tathan.exoconfig.common.network.SyncConfigPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class NetworkRegistry {
    public static void init() {
        PayloadTypeRegistry.playS2C().register(SyncConfigPacket.TYPE, SyncConfigPacket.STREAM_CODEC);
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncConfigPacket.TYPE, (packet, ctx) -> SyncConfigPacket.handle(packet));
    }
}
