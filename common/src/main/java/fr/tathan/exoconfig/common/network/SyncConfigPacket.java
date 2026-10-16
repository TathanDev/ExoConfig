package fr.tathan.exoconfig.common.network;

import fr.tathan.exoconfig.ExoConfig;
import fr.tathan.exoconfig.common.loader.ConfigsRegistry;
import fr.tathan.exoconfig.common.utils.ConfigHolder;
import fr.tathan.exoconfig.common.utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SyncConfigPacket(String configName, String stringConfig) implements CustomPacketPayload {

    public static final Identifier CHANNEL = Identifier.fromNamespaceAndPath(ExoConfig.MOD_ID, "sync_config");
    public static final Type<SyncConfigPacket> TYPE = new Type<>(CHANNEL);
    public static final StreamCodec<FriendlyByteBuf, SyncConfigPacket> STREAM_CODEC = StreamCodec.ofMember(SyncConfigPacket::encode, SyncConfigPacket::new);


    @Override
    public Type<SyncConfigPacket> type() {
        return TYPE;
    }

    public SyncConfigPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readUtf());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.configName);
        buf.writeUtf(this.stringConfig);
    }

    public static void handle(SyncConfigPacket packet) {
        ConfigHolder<?> holder = ConfigsRegistry.getInstance().getConfig(packet.configName);
        if (holder == null) return;

        Object liveConfig = holder.getConfig();
        try {
            // Deserialize the config from the string
            Object serverConfig = ExoConfig.getGson().fromJson(packet.stringConfig, liveConfig.getClass());
            if (serverConfig == null) {
                return;
            }
            Utils.copyFields(serverConfig, liveConfig);
        } catch (Exception e) {
            ExoConfig.LOG.error("Could not apply synced config {}", packet.configName, e);
        }
    }
}
