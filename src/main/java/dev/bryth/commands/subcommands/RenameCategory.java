package dev.bryth.commands.subcommands;

import dev.bryth.managers.FriendsCategoriesManager;
import dev.bryth.utils.MessageUtil;
import dev.bryth.utils.NumUtil;

public class RenameCategory {
    public String getCommandUsage() {
        return "fc rename <old name> <new name>";
    }

    public void processCommand(String[] args) {
        if (args.length != 2) {
            MessageUtil.sendError("Invalid arguments. Usage: " + getCommandUsage());
            return;
        }

        String name = args[1].replaceAll("&", "§");

        if (NumUtil.isPositiveInteger(name)) {
            MessageUtil.sendError("Category names cannot be numbers");
            return;
        }

        if (name.equalsIgnoreCase("best") || name.equalsIgnoreCase("all")) {
            MessageUtil.sendError("You cannot rename a category to " + name);
            return;
        }

        FriendsCategoriesManager.renameCategory(args[0], name);
    }
}
