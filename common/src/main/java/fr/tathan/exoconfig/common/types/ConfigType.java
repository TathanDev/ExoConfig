package fr.tathan.exoconfig.common.types;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import fr.tathan.exoconfig.ExoConfig;

import java.lang.reflect.Type;

public interface ConfigType<T extends ConfigType<T>> {

    void postValidation();

    default JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return new GsonBuilder().setPrettyPrinting().create().toJsonTree(src);
    }

    default T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(json, typeOfT);
    }
}
