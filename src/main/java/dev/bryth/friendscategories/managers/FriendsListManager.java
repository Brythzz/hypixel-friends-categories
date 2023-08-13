package dev.bryth.friendscategories.managers;

import dev.bryth.friendscategories.utils.ListUtil;
import dev.bryth.friendscategories.utils.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class FriendsListManager {

    private boolean catchingFriends = false;
    private List<String> friends;
    private int categorySize;
    private int friendsAdded = 0;
    private final ChatComponentText out = new ChatComponentText(MessageUtil.getSeparator());

    public void sendFilteredFriends(String category) {
        String categoryName = ListUtil.getKeyCaseInsensitive(FriendsCategoriesManager.getCategories(), category);
        if (categoryName == null) {
            MessageUtil.sendError(String.format("Category %s§c does not exist!", category));
            return;
        }

        FriendsCategoriesManager.lock = true;

        List<String> friendsList = FriendsCategoriesManager.getFriends(categoryName);
        out.appendSibling(new ChatComponentText("\n§e" + MessageUtil.padMessage(categoryName) + "\n"));

        friends = new ArrayList<>(friendsList);
        categorySize = friendsList.size();
        catchingFriends = true;

        Minecraft.getMinecraft().thePlayer.sendChatMessage("/fl 1");
    }

    private void sendMessage() {
        for (String friend : friends) {
            out.appendSibling(new ChatComponentText(String.format("§8%s is not on your friends list!\n", friend)));
        }
        out.appendSibling(new ChatComponentText(MessageUtil.getSeparator()));
        MessageUtil.sendRichMessage(out);
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (event.entity == player) {
            MinecraftForge.EVENT_BUS.unregister(this);
            FriendsCategoriesManager.lock = false;
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(final ClientChatReceivedEvent event) {
        boolean isChatMessage = event.type == 0;
        if (!catchingFriends || !isChatMessage) return;

        String message = event.message.getFormattedText();

        String sep = MessageUtil.getSeparator();

        if (!message.startsWith(sep) || !message.endsWith(sep)) return;

        event.setCanceled(true);

        String[] lines = message.split("\n");
        String[] digits = MessageUtil.removeFormatting(lines[1]).split("\\D+");
        int curPage = Integer.parseInt(digits[1]);
        int maxPage = Integer.parseInt(digits[2]);

        List<IChatComponent> components = event.message.getSiblings();
        int componentsCount = components.size();

        for (int i = 0; i < componentsCount; i++) {
            IChatComponent component = components.get(i);
            String unformatted = component.getUnformattedText();
            if (unformatted.contains("--------") || unformatted.startsWith(" ")) continue;

            String text = component.getFormattedText();
            boolean isUsername = text.length() > 2 && text.charAt(0) == '§';
            if (!isUsername) continue;

            String username = ListUtil.getKeyCaseInsensitive(friends, text);
            if (username != null) {
                friends.remove(username);
                out.appendSibling(component).appendSibling(components.get(i + 1)).appendSibling(new ChatComponentText("\n"));
                friendsAdded++;
                i++;
            }
        }

        if (friendsAdded == categorySize || curPage >= maxPage) {
            MinecraftForge.EVENT_BUS.unregister(this);
            catchingFriends = false;
            FriendsCategoriesManager.lock = false;
            sendMessage();
            return;
        }

        Minecraft.getMinecraft().thePlayer.sendChatMessage("/fl " + (curPage + 1));
    }
}
