package io.github.nullpoe.command.sub;

import io.github.nullpoe.Home;
import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.context.MessageType;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadConfigCommand implements SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        MessageContent messageContent = MessageContent.getInstance();

        if (!sender.isOp()) {
            messageContent.getMessageAfterPrefix(MessageType.ERROR, "noPermission").ifPresent(sender::sendMessage);
            return false;
        }

        if (args.length == 0) {
            JavaPlugin plugin = Home.getInstance();
            plugin.reloadConfig();
            messageContent.initialize(plugin.getConfig());
            messageContent.getMessageAfterPrefix(MessageType.NORMAL, "reloadComplete").ifPresent(sender::sendMessage);
            return true;
        }
        return false;
    }
}
