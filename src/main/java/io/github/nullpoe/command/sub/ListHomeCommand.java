package io.github.nullpoe.command.sub;

import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.context.MessageType;
import io.github.nullpoe.inventory.HomeInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListHomeCommand implements SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageContent.getInstance().getMessageAfterPrefix(MessageType.ERROR, "noConsoleCommand").ifPresent(sender::sendMessage);
            return false;
        }

        if (args.length == 0) {
            HomeInventory.getInstance().openInventory(player);
            return true;
        }
        return false;
    }
}
