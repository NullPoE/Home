package io.github.nullpoe.command;

import io.github.nullpoe.command.sub.*;
import io.github.nullpoe.context.MessageContent;
import io.github.nullpoe.context.MessageType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HomeCommand implements CommandExecutor {

    private static final Map<String, SubCommand> subCommandMap = new HashMap<>();

    static {
        subCommandMap.put("추가", new AddHomeCommand());
        subCommandMap.put("제거", new RemoveHomeCommand());
        subCommandMap.put("목록", new ListHomeCommand());
        subCommandMap.put("한도설정", new SetLimitHomeCommand());
        subCommandMap.put("한도", new LimitHomeCommand());
        subCommandMap.put("리로드", new ReloadConfigCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender.isOp()) {
                sender.sendMessage(
                        " §6§l| §f집 도움말 : §c(관리자 전용)",
                        " §e→ §f/집 추가 <이름> : §7<이름>의 집을 현재 서 있는 위치로 추가합니다.",
                        " §e→ §f/집 제거 <이름> : §7<이름>의 집을 제거합니다.",
                        " §e→ §f/집 목록 : §7집 목록 GUI를 오픈합니다.",
                        " §e→ §f/집 한도 : §7자신의 집 한도를 확인합니다.",
                        " §e→ §f/집 한도설정 <플레이어> <개수> : §7<플레이어>의 집 한도를 <개수>로 설정합니다.",
                        " §e→ §f/집 리로드 : §7집 구성파일(콘피그)를 리로드합니다."
                );
                return true;
            } else {
                sender.sendMessage(
                        " §6§l| §f집 도움말",
                        " §e→ §f/집 추가 <이름> : §7<이름>의 집을 현재 서 있는 위치로 추가합니다.",
                        " §e→ §f/집 제거 <이름> : §7<이름>의 집을 제거합니다.",
                        " §e→ §f/집 목록 : §7집 목록 GUI를 오픈합니다.",
                        " §e→ §f/집 한도 : §7자신의 집 한도를 확인합니다."
                );
                return true;
            }
        }

        SubCommand subCommand = subCommandMap.get(args[0]);
        if (subCommand != null) {
            args = Arrays.copyOfRange(args, 1, args.length);
            return subCommand.onCommand(sender, args);
        } else {
            MessageContent.getInstance().getMessageAfterPrefix(MessageType.ERROR, "notExistCommand").ifPresent(sender::sendMessage);
            return false;
        }
    }
}
