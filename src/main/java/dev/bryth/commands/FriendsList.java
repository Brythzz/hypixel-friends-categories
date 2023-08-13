package dev.bryth.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class FriendsList extends CommandBase {
    @Override
    public String getCommandName() {
        return "fl";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "fl <page|category>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        String[] newArgs = new String[args.length + 1];
        newArgs[0] = "list";
        System.arraycopy(args, 0, newArgs, 1, args.length);

        Friends fr = new Friends();
        fr.processCommand(sender, newArgs);
    }
}