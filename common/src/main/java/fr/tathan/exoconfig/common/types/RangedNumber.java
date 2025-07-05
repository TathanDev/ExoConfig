package fr.tathan.exoconfig.common.types;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RangedNumber extends Number implements ConfigType<RangedNumber> {

    private final Number min;
    private final Number max;
    public Number value;

    public RangedNumber(Number min, Number max, Number value) {
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public Number getMin() {
        return min;
    }

    public Number getMax() {
        return max;
    }

    public Number getValue() {
        return value;
    }

    public boolean isInRange() {
        if(value instanceof Integer) {
            return (Integer) value >= (Integer) min && (Integer) value <= (Integer) max;
        } else if(value instanceof Double) {
            return (Double) value >= (Double) min && (Double) value <= (Double) max;
        } else if(value instanceof Float) {
            return (Float) value >= (Float) min && (Float) value <= (Float) max;
        } else if(value instanceof Long) {
            return (Long) value >= (Long) min && (Long) value <= (Long) max;
        }
        throw new IllegalArgumentException("Unsupported number type: " + value.getClass().getName());
    }

    @Override
    public void postValidation() {
        if(!isInRange()) {
            this.value = min;
        }
    }

    @Override
    public JsonElement serialize(RangedNumber src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.add("min", context.serialize(src.getMin()));
        obj.add("max", context.serialize(src.getMax()));
        obj.add("value", context.serialize(src.getValue()));
        return obj;
    }

    @Override
    public RangedNumber deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject obj = json.getAsJsonObject();

        if (obj.get("value").getAsJsonPrimitive().isNumber()) {
            Number min = obj.get("min").getAsNumber();
            Number max = obj.get("max").getAsNumber();
            Number value = obj.get("value").getAsNumber();

            if (value instanceof Double || obj.get("value").getAsString().contains(".")) {
                return new RangedNumber(min.doubleValue(), max.doubleValue(), value.doubleValue());
            } else {
                return new RangedNumber(min, max, value);
            }
        }

        throw new JsonParseException("Invalid JSON for RangedNumber: " + json);
    }

    @Override
    public int intValue() {
        return this.value.intValue();
    }

    @Override
    public long longValue() {
        return this.value.longValue();
    }

    @Override
    public float floatValue() {
        return this.value.floatValue();
    }

    @Override
    public double doubleValue() {
        return this.value.doubleValue();
    }
}
