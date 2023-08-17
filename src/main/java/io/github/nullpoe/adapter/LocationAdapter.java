package io.github.nullpoe.adapter;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(Location location, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("world", location.getWorld().getName());
        jsonObject.addProperty("x", location.getX());
        jsonObject.addProperty("y", location.getY());
        jsonObject.addProperty("z", location.getZ());
        jsonObject.addProperty("yaw", location.getYaw());
        jsonObject.addProperty("pitch", location.getPitch());
        return jsonObject;
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return new Location(
                Bukkit.getWorld(jsonObject.get("world").getAsString()),
                jsonObject.get("x").getAsDouble(),
                jsonObject.get("y").getAsDouble(),
                jsonObject.get("z").getAsDouble(),
                jsonObject.get("yaw").getAsFloat(),
                jsonObject.get("pitch").getAsFloat()
        );
    }
}
