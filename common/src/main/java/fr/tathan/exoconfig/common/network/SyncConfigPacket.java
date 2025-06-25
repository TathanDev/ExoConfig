package fr.tathan.exoconfig.network;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.loader.ConfigsRegistry;
import fr.tathan.exoconfig.utils.ConfigHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class SyncConfigPacket {

    public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath(ExoConfig.MOD_ID, "sync_config");
    public static final StreamCodec<FriendlyByteBuf, SyncConfigPacket> STREAM_CODEC = StreamCodec.ofMember(SyncConfigPacket::encode, buf -> new SyncConfigPacket(buf));

    public final String stringConfig;
    public final String configName;

    public SyncConfigPacket(String stringConfig, String configName)
    {
        this.stringConfig = stringConfig;
        this.configName = configName;
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type()
    {
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public SyncConfigPacket(FriendlyByteBuf buf)
    {
        this.stringConfig = buf.readUtf();
        this.configName = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.stringConfig);
        buf.writeUtf(this.configName);
    }

    public static void handle(PacketContext<SyncConfigPacket> ctx) {
        if (Side.CLIENT.equals(ctx.side())) {
            SyncConfigPacket packet = ctx.message();
            ConfigHolder<?> oldHolder = ConfigsRegistry.getInstance().getConfig(packet.configName);
            if (oldHolder != null) {
                // Deserialize the config from the string
                Object newConfig = ExoConfig.GSON.fromJson(packet.stringConfig, oldHolder.getConfig().getClass());
                // Update the config instance
                oldHolder.setConfig(newConfig);
                // Save the updated config
                ConfigsRegistry.getInstance().registerConfig(newConfig, oldHolder.getConfigInstance());
                ExoConfig.LOG.info("Received and applied config update for: " + packet.configName);
            } else {
                ExoConfig.LOG.warn("Received config update for unknown config: " + packet.configName);
            }


        }
    }

}
