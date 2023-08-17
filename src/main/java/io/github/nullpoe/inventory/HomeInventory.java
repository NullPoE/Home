package io.github.nullpoe.inventory;

import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.context.MessageType;
import io.github.nullpoe.entity.HomeData;
import io.github.nullpoe.entity.PlayerHome;
import io.github.nullpoe.storage.PlayerHomeStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HomeInventory extends InventoryListenerBase {

    private static HomeInventory instance;

    public static HomeInventory getInstance() {
        if (instance == null) instance = new HomeInventory();
        return instance;
    }

    private HomeInventory() {}

    @Override
    public void openInventory(Player player) {
        HomeInventoryHolder homeInventory = new HomeInventoryHolder(player.getUniqueId());
        player.openInventory(homeInventory.getInventory());
        openInventoryAndRegisterEvent(player, homeInventory.getInventory());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && event.getInventory().getHolder() instanceof HomeInventoryHolder) {
            /* HOME SLOT :
            ------------------------------------------------------------------------------------------- */
            event.setCancelled(true);
            int clickedSlot = event.getSlot();
            int[] homeItemSlot = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
            int index = -1;
            for (int i = 0; i < homeItemSlot.length; i++) {
                if (homeItemSlot[i] == clickedSlot) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                PlayerHome playerHome = PlayerHomeStorage.getHomeMap().get(player.getUniqueId());
                if (playerHome != null && index < playerHome.getOwn().size()) {
                    HomeData home = playerHome.getOwn().get(index);
                    player.teleport(home.location());
                    MessageContent.getInstance().getMessageAfterPrefix(MessageType.NORMAL, "teleportHome").ifPresent(message -> {
                        String replacedMessage = message.replace("{homeName}", home.name());
                        player.sendMessage(replacedMessage);
                    });
                }
            }
        }
    }
}
