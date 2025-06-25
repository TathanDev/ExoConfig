package fr.tathan.exoconfig.common.infos;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.internal.Streams;

import java.io.IOException;
import java.lang.reflect.Field;

public class CommentTypeAdapter implements TypeAdapterFactory {

    private final FieldNamingStrategy fieldNamingStrategy;

    public CommentTypeAdapter(FieldNamingStrategy fieldNamingStrategy) {
        this.fieldNamingStrategy = fieldNamingStrategy != null ? fieldNamingStrategy : FieldNamingPolicy.IDENTITY;
    }

    public CommentTypeAdapter() {
        this(null);
    }


    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }

                JsonElement tree = delegate.toJsonTree(value);

                if (tree.isJsonObject()) {
                    JsonObject jsonObject = tree.getAsJsonObject();

                    for (Class<?> current = raw; current != Object.class; current = current.getSuperclass()) {
                        for (Field field : current.getDeclaredFields()) {
                            if (field.isAnnotationPresent(ConfigInfos.FileDescription.class)) {
                                ConfigInfos.FileDescription annotation = field.getAnnotation(ConfigInfos.FileDescription.class);
                                String comment = annotation.value();

                                String jsonFieldName = fieldNamingStrategy.translateName(field);

                                String commentFieldName = jsonFieldName + "_comment";
                                jsonObject.addProperty(commentFieldName, comment);
                            }
                        }
                    }
                }
                Streams.write(tree, out);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                return delegate.read(in);
            }
        };
    }
}