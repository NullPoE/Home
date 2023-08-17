package io.github.nullpoe.command.sub;

import io.github.nullpoe.Home;
import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.context.MessageType;
import io.github.nullpoe.entity.PlayerHome;
import io.github.nullpoe.storage.PlayerHomeStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LimitHomeCommand implements SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        MessageContent messageContent = MessageContent.getInstance();

        if (!(sender instanceof Player player)) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noConsoleCommand").ifPresent(sender::sendMessage);
            return false;
        }

        if (args.length == 0) {
            PlayerHome playerHome = PlayerHomeStorage.getHomeMap().get(player.getUniqueId());
            int limitHomeSize = playerHome.getLimit();
            int ownHomeSize = playerHome.getOwn().size();
            int maxHomes = limitHomeSize - ownHomeSize;

            sender.sendMessage(
                    " §6§l| §f자신의 집 정보 ",
                    "",
                    " §e→ §f추가할 수 있는 최대 한도: " + limitHomeSize + "  ",
                    " §e→ §f추가된 집 개수: " + ownHomeSize + "  ",
                    " §e→ §f추가할 수 있는 집 개수: " + maxHomes + " ",
                    ""
            );
            return false;
        }
        return false;
    }
}
