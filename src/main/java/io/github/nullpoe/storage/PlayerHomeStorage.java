package io.github.nullpoe.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nullpoe.Home;
import io.github.nullpoe.adapter.LocationAdapter;
import io.github.nullpoe.entity.PlayerHome;
import lombok.Getter;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHomeStorage {

    private static PlayerHomeStorage instance;

    public static PlayerHomeStorage getInstance() {
        if (instance == null) instance = new PlayerHomeStorage();
        return instance;
    }

    private PlayerHomeStorage() {}

    private final Gson gson = new GsonBuilder()
                                    .setPrettyPrinting()
                                    .registerTypeAdapter(Location.class, new LocationAdapter())
                                    .create();

    @Getter
    private static final Map<UUID, PlayerHome> homeMap = new HashMap<>();
    private final String BASE_PATH = Home.getInstance().getDataFolder().getPath() + File.separatorChar + "data" + File.separatorChar;

    public void save() {
        File directory = new File(BASE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        homeMap.forEach(((uuid, playerHomes) -> {
            File file = new File(BASE_PATH + uuid + ".json");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
                    gson.toJson(playerHomes, writer);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }));
    }

    public void load() {
        File directory = new File(BASE_PATH);
        File[] files = directory.listFiles(((dir, name) -> name.endsWith(".json")));

        if (files != null) {
            for (File file : files) {
                try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
                    UUID uuid = UUID.fromString(file.getName().replace(".json", ""));
                    PlayerHome playerHome = gson.fromJson(reader, PlayerHome.class);
                    homeMap.put(uuid, playerHome);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
