package dev.bryth.friendscategories.commands.subcommands;

import dev.bryth.friendscategories.managers.FriendsCategoriesManager;
import dev.bryth.friendscategories.utils.MessageUtil;
import dev.bryth.friendscategories.utils.NumUtil;

public class CreateCategory {
    public String getCommandUsage() {
        return "fc create <name>";
    }

    public void processCommand(String[] args) {
        if (args.length != 1) {
            MessageUtil.sendError("Invalid arguments. Usage: " + getCommandUsage());
            return;
        }

        String name = args[0].replaceAll("&", "§");

        if (MessageUtil.removeFormatting(name).length() == 0) {
            MessageUtil.sendError("Category names cannot be empty");
            return;
        }

        if (NumUtil.isPositiveInteger(name)) {
            MessageUtil.sendError("Category names cannot be numbers");
            return;
        }

        if (name.equalsIgnoreCase("best")) {
            MessageUtil.sendError("You cannot create a category with the name " + name);
            return;
        }

        FriendsCategoriesManager.createCategory(name);
    }
}
