package dev.bryth.friendscategories.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;

public class MessageUtil {
    public static void sendMessage(String content) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(content));
    }

    public static void sendRichMessage(ChatComponentText content) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(content);
    }

    public static String padMessage(String content) {
        int spaces = 38 - (removeFormatting(content).length() / 2);
        String spacing = new String(new char[spaces]).replace('\0', ' ');
        return "§r" + spacing + content;
    }

    public static void sendError(String content) {
        sendMessage("§c" + content);
    }

    public static String removeFormatting(String content) {
        return content.replaceAll("§.", "");
    }

    public static ChatComponentText getButton(String text, String hoverText, String command) {
        ChatComponentText component = new ChatComponentText("§7[§e" + text + "§7] ");
        component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        component.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(hoverText)));
        return component;
    }

    public static String getSeparator() {
        return "§9§m-----------------------------------------------------§r";
    }
}
