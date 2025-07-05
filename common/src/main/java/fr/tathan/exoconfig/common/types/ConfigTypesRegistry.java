package fr.tathan.exoconfig.common.types;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ConfigTypesRegistry {

    private static final Set<GsonAdapter<?>> adapters = new HashSet<>();

    public static <T extends ConfigType<T>> void register(Class<T> clazz, Supplier<T> instanceSupplier) {
        JsonSerializer<T> serializer = (src, typeOfSrc, context) -> src.serialize(src, typeOfSrc, context);

        JsonDeserializer<T> deserializer = (json, typeOfT, context) -> {
            T instance = instanceSupplier.get();
            return instance.deserialize(json, typeOfT, context);

        };

        register(clazz, serializer, deserializer);
    }


    public static <T> void register(Type type, JsonSerializer<T> serializer, JsonDeserializer<T> deserializer) {
        adapters.add(new GsonAdapter<>(type, serializer, deserializer));
    }

    public static Set<GsonAdapter<?>> getAdapters() {
        return Collections.unmodifiableSet(adapters);
    }

    public static void registerAdapters(GsonBuilder builder) {
        for (GsonAdapter<?> adapter : adapters) {
            if (adapter.serializer() != null) {
                builder.registerTypeAdapter(adapter.type(), adapter.serializer());
            }
            if (adapter.deserializer() != null) {
                builder.registerTypeAdapter(adapter.type(), adapter.deserializer());
            }
        }
    }

    public record GsonAdapter<T>(Type type, JsonSerializer<T> serializer, JsonDeserializer<T> deserializer) {

    }
}
