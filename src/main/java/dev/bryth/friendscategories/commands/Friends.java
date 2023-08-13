package dev.bryth.friendscategories.commands;

import dev.bryth.friendscategories.managers.FriendsCategoriesManager;
import dev.bryth.friendscategories.managers.FriendsListManager;
import dev.bryth.friendscategories.utils.NumUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

import dev.bryth.friendscategories.utils.MessageUtil;
import net.minecraftforge.common.MinecraftForge;

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

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("list")) {
            sendCommand(args);
            return;
        }

        if (FriendsCategoriesManager.lock) {
            MessageUtil.sendError("Please wait for the friends list to load!");
            return;
        }

        if (args.length == 1 || args[1].equalsIgnoreCase("best") || NumUtil.isPositiveInteger(args[1])) {
            sendCommand(args);
            return;
        }

        FriendsListManager friendsListManager = new FriendsListManager();
        MinecraftForge.EVENT_BUS.register(friendsListManager);
        friendsListManager.sendFilteredFriends(args[1]);
    }
}