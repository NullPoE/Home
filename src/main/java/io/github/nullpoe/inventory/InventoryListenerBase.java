package io.github.nullpoe.inventory;

import io.github.nullpoe.Home;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class InventoryListenerBase {

    protected static final Map<UUID, Listener> listenerMap = new HashMap<>();

    public void onClick(InventoryClickEvent event) {}
    public void onClose(InventoryCloseEvent event) {}
    public abstract void openInventory(Player player);

    protected void openInventoryAndRegisterEvent(Player player, Inventory inventory) {
        player.openInventory(inventory);
        Listener listener = registerInventoryClickEvent(player.getUniqueId());
        listenerMap.put(player.getUniqueId(), listener);
        registerInventoryCloseEvent(player.getUniqueId(), this);
    }

    protected void registerInventoryCloseEvent(UUID uuid, InventoryListenerBase listenerManager) {
        Server server = Home.getInstance().getServer();
        Listener closeEventListener = new Listener() {};

        server.getPluginManager().registerEvent(InventoryCloseEvent.class, closeEventListener, EventPriority.LOWEST, (listeners, event) -> {
            if (event instanceof InventoryCloseEvent closeEvent) {
                if (uuid.equals(closeEvent.getPlayer().getUniqueId())) {
                    Listener listener = listenerMap.remove(uuid);
                    if (listener != null) {
                        InventoryClickEvent.getHandlerList().unregister(listener);
                    }
                    HandlerList.unregisterAll(closeEventListener);
                    listenerManager.onClose(closeEvent);
                }
            }
        }, Home.getInstance());
    }

    protected Listener registerInventoryClickEvent(UUID uuid) {
        Server server = Home.getInstance().getServer();
        Listener listener = new Listener() {};

        server.getPluginManager().registerEvent(InventoryClickEvent.class, listener, EventPriority.LOWEST, (listeners, event) -> {
            if (event instanceof InventoryClickEvent clickEvent) {
                if (uuid.equals(clickEvent.getWhoClicked().getUniqueId())) {
                    onClick(clickEvent);
                }
            }
        }, Home.getInstance());
        return listener;
    }
}
