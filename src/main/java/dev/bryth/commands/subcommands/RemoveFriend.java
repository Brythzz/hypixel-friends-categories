package dev.bryth.commands.subcommands;

import dev.bryth.utils.MessageUtil;
import dev.bryth.managers.FriendsCategoriesManager;

public class RemoveFriend {
    public String getCommandUsage() {
        return "fc remove <category> <player>";
    }

    public void processCommand(String[] args) {
        if (args.length != 2) {
            MessageUtil.sendError("Invalid arguments. Usage: " + getCommandUsage());
            return;
        }

        FriendsCategoriesManager.removeFriend(args[0], args[1]);
    }
}
