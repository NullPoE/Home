package io.github.nullpoe.inventory;

import io.github.nullpoe.Home;
import io.github.nullpoe.builder.ItemBuilder;
import io.github.nullpoe.entity.HomeData;
import io.github.nullpoe.entity.PlayerHome;
import io.github.nullpoe.storage.PlayerHomeStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HomeInventoryHolder implements InventoryHolder {

    private final Inventory inventory;
    private final UUID uniqueId;

    public HomeInventoryHolder(UUID uniqueId) {
        this.uniqueId = uniqueId;
        inventory = Home.getInstance().getServer().createInventory(this, 9 * 6, "홈 메뉴");
    }

    @Override
    public @NotNull Inventory getInventory() {
        /* PLAYER_HOME ITEM :
        ------------------------------------------------------------------------------------------- */
        int[] homeItemSlot = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

        int limitHomeSize = 0;
        int ownHomeSize = 0;

        PlayerHome playerHome = PlayerHomeStorage.getHomeMap().get(uniqueId);
        if (playerHome != null) {
            int index = 0;
            for (HomeData home : playerHome.getOwn()) {
                if (index >= homeItemSlot.length) break;

                String homeName = home.name();
                Location homeLocation = home.location();
                String x = String.format("%.3f", homeLocation.getX());
                String y = String.format("%.5f", homeLocation.getY());
                String z = String.format("%.3f", homeLocation.getZ());

                ItemStack homeItem = new ItemBuilder(Material.IRON_DOOR)
                        .setDisplayName(" &6&l| &f" + homeName + " ")
                        .setLore(
                                "",
                                " &e→ &fX 좌표 : " + x + "  ",
                                " &e→ &fY 좌표 : " + y + "  ",
                                " &e→ &fZ 좌표 : " + z + "  ",
                                "",
                                " &e→ &f클릭 시 &6" + homeName + "&f(으)로 이동합니다.  ",
                                ""
                        )
                        .build();

                inventory.setItem(homeItemSlot[index], homeItem);
                index++;
            }
            limitHomeSize = playerHome.getLimit();
            ownHomeSize = playerHome.getOwn().size();
        }

         /* PLAYER_HEAD ITEM :
        ------------------------------------------------------------------------------------------- */
        int playerHeadItemSlot = 4;
        int maxHomes = limitHomeSize - ownHomeSize;
        ItemStack playerHeadItem = new ItemBuilder(Material.PLAYER_HEAD)
                .setOwner(uniqueId)
                .setDisplayName(" &6&l| &f자신의 집 정보 ")
                .setLore(
                        "",
                        " &e→ &f추가할 수 있는 최대 한도: " + limitHomeSize + "  ",
                        " &e→ &f추가된 집 개수: " + ownHomeSize + "  ",
                        " §e→ §f추가할 수 있는 집 개수: " + maxHomes + " ",
                        ""
                )
                .build();

        inventory.setItem(playerHeadItemSlot, playerHeadItem);

        /* FILTER ITEM :
        ------------------------------------------------------------------------------------------- */
        int[] filterItemSlot = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53};
        ItemStack filterItem = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
                .setDisplayName("&r")
                .build();

        for (int slot : filterItemSlot) {
            inventory.setItem(slot, filterItem);
        }

        int[] filterItemSlot2 = new int[]{9, 18, 27, 36, 17, 26, 35, 44};
        ItemStack filterItem2 = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
                .setDisplayName("&r")
                .build();

        for (int slot : filterItemSlot2) {
            inventory.setItem(slot, filterItem2);
        }

        return inventory;
    }
}
