package io.github.nullpoe.command.sub;

import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.context.MessageType;
import io.github.nullpoe.entity.HomeData;
import io.github.nullpoe.entity.PlayerHome;
import io.github.nullpoe.storage.PlayerHomeStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AddHomeCommand implements SubCommand {

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
                playerHome = new PlayerHome();
                PlayerHomeStorage.getHomeMap().put(uuid, playerHome);
                messageContent.getMessageAfterPrefix(MessageType.ERROR, "limitHomeAmount").ifPresent(sender::sendMessage);
                return false;
            } else {
                if (playerHome.getOwn().size() >= playerHome.getLimit()) {
                    messageContent.getMessageAfterPrefix(MessageType.ERROR, "limitHomeAmount").ifPresent(sender::sendMessage);
                    return false;
                }

                for (HomeData existingHome : playerHome.getOwn()) {
                    if (existingHome.name().equalsIgnoreCase(homeName)) {
                        messageContent.getMessageAfterPrefix(MessageType.ERROR, "alreadyHomeName").ifPresent(message -> {
                            String replacedMessage = message.replace("{homeName}", homeName);
                            sender.sendMessage(replacedMessage);
                        });
                        return false;
                    }
                }
                HomeData homeData = new HomeData(homeName, player.getLocation());
                playerHome.getOwn().add(homeData);
            }

            messageContent.getMessageAfterPrefix(MessageType.NORMAL, "addHome").ifPresent(message -> {
                String replacedMessage = message.replace("{homeName}", homeName);
                sender.sendMessage(replacedMessage);
            });
        }
        return true;
    }
}
