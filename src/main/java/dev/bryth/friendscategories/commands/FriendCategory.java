package dev.bryth.friendscategories.commands;

import dev.bryth.friendscategories.managers.FriendsCategoriesManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

import dev.bryth.friendscategories.commands.subcommands.*;

import dev.bryth.friendscategories.utils.MessageUtil;
import net.minecraft.util.ChatComponentText;

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
        List<String> categories =  FriendsCategoriesManager.getCategories();
        if (categories.isEmpty()) {
            MessageUtil.sendMessage(MessageUtil.getSeparator() + "\n§cYou have no categories! Create one with /fc create <name>\n" + MessageUtil.getSeparator());
            return;
        }

        ChatComponentText message = new ChatComponentText(MessageUtil.getSeparator() + "§6Categories\n");
        for (String category : categories) {
            ChatComponentText button = MessageUtil.getButton(category, "§eSee friends in the category " + category, "/f list " + category);
            message.appendSibling(button);
        }
        message.appendSibling(new ChatComponentText("\n" + MessageUtil.getSeparator()));
        MessageUtil.sendRichMessage(message);
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
