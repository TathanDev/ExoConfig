package fr.tathan.exoconfig.common.utils;


import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class ProxyExclusionAdapterFactory implements TypeAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (Proxy.class.isAssignableFrom(type.getRawType())) {
            return (TypeAdapter<T>) new TypeAdapter<Object>() {
                @Override
                public void write(JsonWriter out, Object value) throws IOException {
                    out.nullValue();

                }

                @Override
                public Object read(JsonReader in) throws IOException {
                    in.skipValue();
                    return null;
                }
            };
        }
        return null; // Let Gson handle other types normally
    }
}