package io.github.nullpoe.context;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessageContent {

    private static MessageContent instance;

    public static MessageContent getInstance() {
        if (instance == null) instance = new MessageContent();
        return instance;
    }

    private MessageContent() {}

    private final Map<MessageType, Map<String, String>> messageMap = new HashMap<>();

    public void initialize(FileConfiguration file) {
        messageMap.clear();
        for (MessageType type : MessageType.values()) {
            ConfigurationSection configSection = file.getConfigurationSection(type.getKey());
            if (configSection != null) {
                initializeMessages(type, configSection);
            }
        }
    }

    private void initializeMessages(MessageType type, ConfigurationSection configSection) {
        Map<String, String> messages = messageMap.computeIfAbsent(type, key -> new HashMap<>());
        for (String key : configSection.getKeys(false)) {
            messages.put(key, ChatColor.translateAlternateColorCodes('&', configSection.getString(key)));
        }
    }

    public Optional<String> getMessage(MessageType type, String key) {
        return Optional.ofNullable(messageMap.getOrDefault(type, Collections.emptyMap()).get(key));
    }

    public Optional<String> getMessageAfterPrefix(MessageType type, String key) {
        String prefix = getMessage(MessageType.NORMAL, "prefix").orElse("");
        return getMessage(type, key).map(message -> prefix + message);
    }
}
