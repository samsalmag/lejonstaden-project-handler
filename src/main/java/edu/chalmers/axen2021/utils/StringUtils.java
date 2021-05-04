package edu.chalmers.axen2021.utils;

public class StringUtils {

    public static String removeTrailingZeros(Number d) {
        return String.valueOf(d).replaceAll("[0]*$", "").replaceAll("\\.$", "");
    }

    public static String removeTrailingZeros(String s) {
        return s.replaceAll("[0]*$", "").replaceAll("\\.$", "");
    }
}
