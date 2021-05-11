package edu.chalmers.axen2021.utils;

public class StringUtils {

    public static String removeTrailingZeros(Double d) {
        return String.valueOf(d).replaceAll("[0]*$", "").replaceAll("\\.$", "");
    }

    public static String removeBeginningZeros(int i) {
        return String.valueOf(i).replaceFirst("^0+(?!$)", "");
    }

    public static String removeTrailingZeros(String s) {
        return s.replaceAll("[0]*$", "").replaceAll("\\.$", "");
    }

    public static Double convertToDouble(String s) {
        s = s.replaceAll(",", ".");
        return Double.parseDouble(s);
    }
}
