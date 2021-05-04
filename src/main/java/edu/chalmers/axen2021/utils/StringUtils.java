package edu.chalmers.axen2021.utils;

public class StringUtils {

    public static String removeTrailingZeros(double d) {
        return String.valueOf(d).replaceAll("[0]*$", "").replaceAll("\\.$", "");
    }
}
