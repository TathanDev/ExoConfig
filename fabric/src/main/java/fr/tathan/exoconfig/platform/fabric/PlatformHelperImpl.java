package fr.tathan.exoconfig.platform.fabric;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.nio.file.Path;

public class PlatformHelperImpl {
    public static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static void sendToClient(CustomPacketPayload payload, ServerPlayer player) {
        ServerPlayNetworking.send(player, payload);
    }
}
