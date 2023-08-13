package dev.bryth.friendscategories.commands.subcommands;

import dev.bryth.friendscategories.managers.FriendsCategoriesManager;
import dev.bryth.friendscategories.utils.MessageUtil;

public class DeleteCategory {
    public String getCommandUsage() {
        return "fc delete <category>";
    }

    public void processCommand(String[] args) {
        if (args.length != 1) {
            MessageUtil.sendError("Invalid arguments. Usage: " + getCommandUsage());
            return;
        }

        String name = args[0].replaceAll("&", "§");
        FriendsCategoriesManager.deleteCategory(MessageUtil.removeFormatting(name));
    }
}
