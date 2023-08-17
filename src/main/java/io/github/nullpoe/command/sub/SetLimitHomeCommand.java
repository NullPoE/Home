package io.github.nullpoe.command.sub;

import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.context.MessageType;
import io.github.nullpoe.entity.PlayerHome;
import io.github.nullpoe.storage.PlayerHomeStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLimitHomeCommand implements SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        MessageContent messageContent = MessageContent.getInstance();

        if (!sender.isOp()) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noPermission").ifPresent(sender::sendMessage);
            return false;
        }

        if (!(sender instanceof Player)) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noConsoleCommand").ifPresent(sender::sendMessage);
            return false;
        }

        if (args.length == 0) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noPlayerName").ifPresent(sender::sendMessage);
            return false;
        }

        if (args.length == 1) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noLimitAmount").ifPresent(sender::sendMessage);
            return false;
        }

        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);

        PlayerHome playerHome = PlayerHomeStorage.getHomeMap().get(targetPlayer.getUniqueId());
        if (playerHome == null) {
            playerHome = new PlayerHome();
            PlayerHomeStorage.getHomeMap().put(targetPlayer.getUniqueId(), playerHome);
        }

        int limit;
        try {
            limit = Integer.parseInt(args[1]);
            if (limit < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noVaildLimitAmount");
            return false;
        }
        playerHome.setLimit(limit);

        messageContent.getMessageAfterPrefix(MessageType.NORMAL, "setHomeLimit").ifPresent(message -> {
            String replacedMessage = message
                    .replace("{limit}", String.valueOf(limit))
                    .replace("{targetPlayer}", targetPlayer.getName());
            sender.sendMessage(replacedMessage);
        });
        return false;
    }
}
