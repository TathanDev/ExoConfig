package fr.tathan.exoconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import commonnetwork.api.Dispatcher;
import fr.tathan.exoconfig.client.ExodusClient;
import fr.tathan.exoconfig.common.infos.CommentTypeAdapter;
import fr.tathan.exoconfig.common.loader.ConfigsRegistry;
import fr.tathan.exoconfig.common.network.NetworkRegistry;
import fr.tathan.exoconfig.common.network.SyncConfigPacket;
import fr.tathan.exoconfig.common.Config;
import fr.tathan.exoconfig.common.utils.ProxyExclusionAdapterFactory;
import fr.tathan.exoconfig.platform.PlatformHelper;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExoConfig {

    public static final String MOD_ID = "exoconfig";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setLenient()
            .registerTypeAdapterFactory(new CommentTypeAdapter())
            .registerTypeAdapterFactory(new ProxyExclusionAdapterFactory())
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .create();

    public static Config EXO_CONFIG;

    public static void init() {
        ExodusClient.init();
        NetworkRegistry.init();
        EXO_CONFIG = ConfigsRegistry.getInstance().registerConfig(new Config(), EXO_CONFIG);

        PlatformHelper.registerConfigScreen(MOD_ID, EXO_CONFIG);
    }

    public static void syncConfigs(ServerPlayer player, boolean joined) {
        if (joined) {
            ConfigsRegistry.getInstance().getConfigs().forEach((key, config) -> {
                if (config.isSyncable()) {
                    Dispatcher.sendToClient(new SyncConfigPacket(key, GSON.toJson(config)), player);
                }
            });
        }
    }
}
