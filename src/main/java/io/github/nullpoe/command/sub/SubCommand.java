package io.github.nullpoe.command.sub;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    boolean onCommand(CommandSender sender, String[] args);
}
