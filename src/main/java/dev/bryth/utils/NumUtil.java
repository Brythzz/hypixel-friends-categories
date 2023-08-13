package dev.bryth.utils;

public class NumUtil {
    public static boolean isPositiveInteger(String num) {
        try {
            return Integer.parseInt(num) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
