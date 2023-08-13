package dev.bryth.friendscategories.managers;

import com.google.gson.Gson;
import dev.bryth.friendscategories.utils.ListUtil;
import dev.bryth.friendscategories.utils.MessageUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class FriendsCategoriesManager {
    public static class FriendCategories {
        private final Map<String, List<String>> categories = new HashMap<>();
    }

    private static FriendCategories fc = new FriendCategories();

    private static File configFile;

    public static boolean lock = false; // If a command is being processed

    public static void addFriend(String category, String name) {
        String categoryName = ListUtil.getKeyCaseInsensitive(getCategories(), category);
        if (categoryName == null) {
            MessageUtil.sendError(String.format("A category with the name %s does not exist", category));
            return;
        }

        List<String> categoryList = fc.categories.get(categoryName);

        if (ListUtil.containsIgnoreCase(categoryList, name)) {
            MessageUtil.sendError(String.format("%s is already in the category %s", name, categoryName));
            return;
        }

        categoryList.add(name);
        MessageUtil.sendMessage(String.format("§aAdded %s to the category %s", name, categoryName));
        save();
    }

    public static void removeFriend(String category, String name) {
        String categoryName = ListUtil.getKeyCaseInsensitive(getCategories(), category);
        if (categoryName == null) {
            MessageUtil.sendError(String.format("A category with the name %s does not exist", category));
            return;
        }

        List<String> categoryList = fc.categories.get(categoryName);

        if (!ListUtil.removeIgnoreCase(categoryList, name)) {
            MessageUtil.sendError(String.format("%s is not in the category %s", name, categoryName));
            return;
        }

        MessageUtil.sendMessage(String.format("§aRemoved %s from the category %s", name, categoryName));
        save();
    }

    public static void renameCategory(String oldName, String newName) {
        String categoryName = ListUtil.getKeyCaseInsensitive(getCategories(), oldName);
        if (categoryName == null) {
            MessageUtil.sendError(String.format("A category with the name %s§c does not exist", oldName));
            return;
        }
        List<String> categoryList = fc.categories.remove(categoryName);
        fc.categories.put(newName, categoryList);

        MessageUtil.sendMessage(String.format("§aRenamed the category %s§a to %s", categoryName, newName));
        save();
    }

    public static void createCategory(String name) {
        List<String> categoryList = fc.categories.get(name);
        if (categoryList != null) {
            MessageUtil.sendError(String.format("A category with the name %s already exists", name));
            return;
        }

        fc.categories.put(name, new ArrayList<>());
        MessageUtil.sendMessage(String.format("§aCreated the category %s", name));
        save();
    }

    public static void deleteCategory(String name) {
        String categoryName = ListUtil.getKeyCaseInsensitive(getCategories(), name);
        if (categoryName == null) {
            MessageUtil.sendError(String.format("A category with the name %s does not exist", name));
            return;
        }

        fc.categories.remove(categoryName);
        MessageUtil.sendMessage(String.format("§aDeleted the category %s", categoryName));
        save();
    }

    public static List<String> getFriends(String name) {
        String categoryName = ListUtil.getKeyCaseInsensitive(getCategories(), name);
        return categoryName == null ? null : fc.categories.get(categoryName);
    }

    public static List<String> getCategories() {
        return new ArrayList<>(fc.categories.keySet());
    }

    /**
     * Writes the friend categories to the json file
     */
    private static void save() {
        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter(configFile);
            gson.toJson(fc, writer);
            writer.close();
        } catch (Exception e) {
            MessageUtil.sendError("An error occurred while saving the friend categories");
            e.printStackTrace();
        }
    }

    /**
     * Initializes the friend categories json file
     * @param file
     */
    public static void init(File file) {
        configFile = file;

        Gson gson = new Gson();
        try {

            if (file.createNewFile()) { // New file created
                gson.toJson(null, new java.io.FileWriter(file));
            }
            else {
                FileReader reader = new FileReader(file);
                FriendCategories categories = gson.fromJson(reader, FriendCategories.class);

                if (categories != null) fc = categories;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
