package fr.tathan.exoconfig.common.types;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;

public class ResourceLocationType {

    public static JsonElement serialize(ResourceLocation src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    public static ResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (json.isJsonPrimitive()) {
            JsonPrimitive obj = json.getAsJsonPrimitive();
            return ResourceLocation.parse(obj.getAsString());

        }
        throw new IllegalArgumentException("Invalid JSON for ResourceLocation: " + json);
    }

}
