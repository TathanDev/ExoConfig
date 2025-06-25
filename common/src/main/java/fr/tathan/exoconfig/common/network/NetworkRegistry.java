package fr.tathan.exoconfig.common.network;

import commonnetwork.api.Network;

public class NetworkRegistry {

    public static void init()
    {
        Network
                .registerPacket(SyncConfigPacket.type(), SyncConfigPacket.class, SyncConfigPacket.STREAM_CODEC, SyncConfigPacket::handle);
    }


}
