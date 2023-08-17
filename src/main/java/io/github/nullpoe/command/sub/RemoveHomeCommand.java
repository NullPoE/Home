package io.github.nullpoe.command.sub;

import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.context.MessageType;
import io.github.nullpoe.entity.PlayerHome;
import io.github.nullpoe.storage.PlayerHomeStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RemoveHomeCommand implements SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        MessageContent messageContent = MessageContent.getInstance();

        if (!(sender instanceof Player player)) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noConsoleCommand").ifPresent(sender::sendMessage);
            return false;
        }

        if (args.length == 0) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noHomeName").ifPresent(sender::sendMessage);
            return false;
        }

        if (args.length == 1) {
            UUID uuid = player.getUniqueId();
            String homeName = args[0];
            PlayerHome playerHome = PlayerHomeStorage.getHomeMap().get(uuid);

            if (playerHome == null) {
                messageContent.getMessageAfterPrefix(MessageType.ERROR, "noHomeExists").ifPresent(sender::sendMessage);
                return false;
            } else {
                boolean removed = playerHome.getOwn().removeIf(home -> home.name().equalsIgnoreCase(homeName));

                if (!removed) {
                    messageContent.getMessageAfterPrefix(MessageType.ERROR, "noHomeExists").ifPresent(message -> {
                        String replacedMessage = message.replace("{homeName}", homeName);
                        sender.sendMessage(replacedMessage);
                    });
                    return false;
                }
            }

            messageContent.getMessageAfterPrefix(MessageType.NORMAL, "removeHome").ifPresent(message -> {
                String replacedMessage = message.replace("{homeName}", homeName);
                sender.sendMessage(replacedMessage);
            });
        }
        return true;
    }
}
