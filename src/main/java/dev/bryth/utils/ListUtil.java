package dev.bryth.utils;

import java.util.List;

public class ListUtil {
    public static boolean containsIgnoreCase(List<String> list, String str) {
        for (String s : list) {
            if (s.equalsIgnoreCase(str))  return true;
        }
        return false;
    }

    public static boolean removeIgnoreCase(List<String> list, String str) {
        for (String s : list) {
            if (s.equalsIgnoreCase(str)) {
                return list.remove(s);
            }
        }
        return false;
    }

    // Defeats the purpose of the HashMap but performance isn't a concern here
    public static String getKeyCaseInsensitive(List<String> list, String key) {
        for (String s : list) {
            String keyName = MessageUtil.removeFormatting(s);
            if (keyName.equalsIgnoreCase(MessageUtil.removeFormatting(key))) return s;
        }
        return null;
    }
}
