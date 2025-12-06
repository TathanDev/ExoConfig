package fr.tathan.exoconfig.platform.neoforge;

import fr.tathan.exoconfig.client.screen.ConfigScreen;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.PacketDistributor;

import java.nio.file.Path;

public class PlatformClientHelperImpl {

    public static void registerConfigScreen(String modid, Object config) {
        ModContainer container = ModList.get().getModContainerById(modid).orElseThrow();

        container.registerExtensionPoint(IConfigScreenFactory.class, (cont, screen) -> new ConfigScreen<>(screen, config));

    }

}
