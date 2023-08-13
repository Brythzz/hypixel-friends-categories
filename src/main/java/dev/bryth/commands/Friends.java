package dev.bryth.commands;

import dev.bryth.managers.FriendsCategoriesManager;
import dev.bryth.managers.FriendsListManager;
import dev.bryth.utils.NumUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

import dev.bryth.utils.MessageUtil;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

// Note: we do not override the /fl command
public class Friends extends CommandBase {
    @Override
    public String getCommandName() {
        return "friends";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("f", "friend");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "friend";
    }

    private void sendCommand(String[] args) {
        String command = args.length == 0 ? "/f" : "/f " + String.join(" ", args);
        Minecraft.getMinecraft().thePlayer.sendChatMessage(command);
    }

    @Override // We only override the f list command
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("list")) {
            sendCommand(args);
            return;
        }

        if (args.length == 1) {
            List<String> categories =  FriendsCategoriesManager.getCategories();
            if (categories.isEmpty()) {
                MessageUtil.sendMessage(MessageUtil.getSeparator() + "\n§cYou have no categories! Create one with /fc create <name>\n" + MessageUtil.getSeparator());
                return;
            }

            ChatComponentText message = new ChatComponentText(MessageUtil.getSeparator() + "§6Categories\n");
            for (String category : categories) {
                ChatComponentText button = MessageUtil.getButton(category, "See friends in the category " + category, "/f list " + category);
                message.appendSibling(button);
            }
            message.appendSibling(new ChatComponentText("\n" + MessageUtil.getSeparator()));
            MessageUtil.sendRichMessage(message);
            return;
        }

        if (args[1].equalsIgnoreCase("all")) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/f list");
            return;
        }

        if (args[1].equalsIgnoreCase("best") || NumUtil.isPositiveInteger(args[1])) {
            sendCommand(args);
            return;
        }

        if (FriendsCategoriesManager.lock) {
            MessageUtil.sendError("Please wait for the friends list to load!");
            return;
        }

        FriendsCategoriesManager.lock = true;
        FriendsListManager friendsListManager = new FriendsListManager();
        MinecraftForge.EVENT_BUS.register(friendsListManager);
        friendsListManager.sendFilteredFriends(args[1]);
    }
}