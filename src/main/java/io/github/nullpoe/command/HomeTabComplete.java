package io.github.nullpoe.command;

import io.github.nullpoe.Home;
import io.github.nullpoe.entity.HomeData;
import io.github.nullpoe.entity.PlayerHome;
import io.github.nullpoe.storage.PlayerHomeStorage;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeTabComplete implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tabList = new ArrayList<>();

        if (args.length == 1) {
            tabList.addAll(List.of("추가", "목록", "제거", "한도"));
            if (sender.isOp()) tabList.addAll(List.of("한도설정", "리로드"));
        }

        if (args.length == 2) {
            if ("한도설정".equals(args[0]) && sender.isOp()) {
                for (OfflinePlayer offlinePlayer : Home.getInstance().getServer().getOfflinePlayers()) {
                    String offlinePlayerName = offlinePlayer.getName();
                    tabList.add(offlinePlayerName);
                }
            }

            else if ("추가".equals(args[0])) {
                tabList.add("추가할 집 이름을 입력해주세요.");
            }

            else if ("제거".equals(args[0]) && sender instanceof Player) {
                Player player = (Player) sender;
                PlayerHome playerHome = PlayerHomeStorage.getHomeMap().get(player.getUniqueId());
                for (HomeData homeData : playerHome.getOwn()) {
                    String homeName = homeData.name();
                    tabList.add(homeName);
                }
            }
        }

        if (args.length == 3) {
            if ("한도설정".equals(args[0]) && sender.isOp()) {
                tabList = IntStream.rangeClosed(1, 28)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.toList());
                tabList.addAll(tabList);
            }
        }

        return StringUtil.copyPartialMatches(args[args.length - 1], tabList, new ArrayList<>());
    }
}
