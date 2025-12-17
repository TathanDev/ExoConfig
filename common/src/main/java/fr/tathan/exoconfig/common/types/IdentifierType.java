package fr.tathan.exoconfig.common.types;

import com.google.gson.*;
import net.minecraft.resources.Identifier;

import java.lang.reflect.Type;

public class IdentifierType {

    public static JsonElement serialize(Identifier src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    public static Identifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (json.isJsonPrimitive()) {
            JsonPrimitive obj = json.getAsJsonPrimitive();
            return Identifier.parse(obj.getAsString());

        }
        throw new IllegalArgumentException("Invalid JSON for Identifier: " + json);
    }

}
