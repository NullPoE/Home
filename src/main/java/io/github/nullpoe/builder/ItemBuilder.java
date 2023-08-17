package io.github.nullpoe.builder;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(ItemStack item) {
        this.itemStack = item;
        this.itemMeta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        Preconditions.checkNotNull(name, "The name cannot be null.");
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<String> coloredLore = lore.stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());
        itemMeta.setLore(coloredLore);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setCustomModelData(int id) {
        itemMeta.setCustomModelData(id);
        return this;
    }

    public ItemBuilder setOwner(UUID owner) {
        Preconditions.checkNotNull(owner, "Owner UUID cannot be null.");
        Preconditions.checkArgument(itemMeta instanceof SkullMeta, "ItemMeta must be an instance of SkullMeta");
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(owner);
        ((SkullMeta) itemMeta).setOwningPlayer(offlinePlayer);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
