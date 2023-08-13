package dev.bryth.commands;

import dev.bryth.managers.FriendsCategoriesManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

import dev.bryth.commands.subcommands.*;

import dev.bryth.utils.MessageUtil;

public class FriendCategory extends CommandBase {
    @Override
    public String getCommandName() {
        return "friendcategory";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("fc", "friendcategories");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "fc <add|remove|create|rename> <arguments>";
    }

    private void sendCategories() {
        List<String> categories = FriendsCategoriesManager.getCategories();
        if (categories.isEmpty())
            MessageUtil.sendError("You have no categories! Create one with /fc create <name>");
        else
            MessageUtil.sendMessage("§eCategories: " + String.join("§r, ", FriendsCategoriesManager.getCategories()));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sendCategories();
            return;
        }

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (args[0]) {
            case "add":
                new AddFriend().processCommand(subArgs);
                break;
            case "remove":
                new RemoveFriend().processCommand(subArgs);
                break;
            case "create":
                new CreateCategory().processCommand(subArgs);
                break;
            case "rename":
                new RenameCategory().processCommand(subArgs);
                break;
            case "delete":
                new DeleteCategory().processCommand(subArgs);
                break;
            default:
                MessageUtil.sendError("Invalid arguments. Usage: " + getCommandUsage(sender));
                break;
        }
    }
}
