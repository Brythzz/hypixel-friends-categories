package dev.bryth.commands.subcommands;

import dev.bryth.managers.FriendsCategoriesManager;
import dev.bryth.utils.MessageUtil;

public class AddFriend {
    public String getCommandUsage() {
        return "fc add <category> <player>";
    }

    public void processCommand(String[] args) {
        if (args.length != 2) {
            MessageUtil.sendError("Invalid arguments. Usage: " + getCommandUsage());
            return;
        }

        boolean isValidName = args[1].matches("\\w+");
        if (!isValidName) {
            MessageUtil.sendError("Invalid name! Minecraft names can only contain letters, numbers, and underscores");
            return;
        }

        FriendsCategoriesManager.addFriend(args[0], args[1]);
    }
}
