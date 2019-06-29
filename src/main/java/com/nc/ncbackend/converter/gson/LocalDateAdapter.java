package com.nc.ncbackend.converter.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public JsonElement serialize(LocalDate t, Type type, JsonSerializationContext jsc) {
        return new JsonPrimitive(t.format(formatter));
    }

    @Override
    public LocalDate deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        return LocalDate.parse(je.getAsString(), formatter);
    }

}
