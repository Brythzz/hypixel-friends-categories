package dev.bryth;

import dev.bryth.managers.FriendsCategoriesManager;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import dev.bryth.commands.FriendCategory;
import dev.bryth.commands.FriendsList;
import dev.bryth.commands.Friends;

@Mod(modid = "friends-categories", useMetadata=true)
public class FriendsCategories {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File configFile = new File(event.getModConfigurationDirectory(), "friends-categories.json");
        FriendsCategoriesManager.init(configFile);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new FriendCategory());
        ClientCommandHandler.instance.registerCommand(new FriendsList());
        ClientCommandHandler.instance.registerCommand(new Friends());
    }
}
