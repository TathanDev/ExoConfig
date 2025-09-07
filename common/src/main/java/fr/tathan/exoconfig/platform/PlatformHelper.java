package fr.tathan.exoconfig.platform;


import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.nio.file.Path;

public class PlatformHelper {

    @ExpectPlatform
    public static Path getConfigPath() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerConfigScreen(String modid, Object config) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void sendToClient(CustomPacketPayload payload, ServerPlayer player) {
        throw new AssertionError();
    }
}
