package dev.bryth.commands.subcommands;

import dev.bryth.managers.FriendsCategoriesManager;
import dev.bryth.utils.MessageUtil;

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
