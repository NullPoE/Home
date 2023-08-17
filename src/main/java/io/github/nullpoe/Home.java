package io.github.nullpoe;

import io.github.nullpoe.command.HomeCommand;
import io.github.nullpoe.command.HomeTabComplete;
import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.storage.PlayerHomeStorage;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Home extends JavaPlugin {

    @Getter
    private static Home instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        MessageContent.getInstance().initialize(getConfig());
        PlayerHomeStorage.getInstance().load();

        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(new HomeTabComplete());
    }

    @Override
    public void onDisable() {
        PlayerHomeStorage.getInstance().save();
    }
}
