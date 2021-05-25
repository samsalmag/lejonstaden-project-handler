package edu.chalmers.axen2021.utils;

/**
 * Utilities for String objects.
 * @author Malte Åkvist
 * @author Oscar Arvidson
 */
public class StringUtils {

    /**
     * The regex used to check project name input. Disallows illegal characters.
     */
    public static String projectNameRegex = "^$|^[0-9a-zA-ZåÅäÄöÖ\\s\\^\\&\\'\\@\\{\\}\\[\\]\\,\\$\\=\\!\\-\\#\\(\\)\\%\\+\\~\\_ ]+$";

    /**
     * Removes trailing/unnecessary zeros from a double.
     * @param d The double.
     * @return The new double as a String.
     */
    public static String removeTrailingZeros(Double d) {
        return String.valueOf(d).replaceAll("[0]*$", "").replaceAll("\\.$", "");
    }

    /**
     * Removes unnecessary zeros from the beginning of a double.
     * @param i The double.
     * @return The new double as a String.
     */
    public static String removeBeginningZeros(int i) {
        return String.valueOf(i).replaceFirst("^0+(?!$)", "");
    }

    /**
     * Removes trailing/unnecessary zeros from a String.
     * @param s The string.
     * @return The new String.
     */
    public static String removeTrailingZeros(String s) {
        return s.replaceAll("[0]*$", "").replaceAll("\\.$", "");
    }

    /**
     * Reformats a String to an acceptable double value.
     * @param s The string.
     * @return The double value.
     */
    public static Double convertToDouble(String s) {
        s = s.replaceAll(",", ".");
        s = s.replaceAll("%", "");
        return Double.parseDouble(s);
    }

    public static String separateKkr(double d) {
        Integer i = (int) d;
        String s = i.toString();
        StringBuilder sb = new StringBuilder(s);
        if (s.length() > 2) {
            for (int j = s.length()-3 ; j >=0 ; j-- ) {
                sb.insert(j, " ");
                j -= 2;
            }
        }
        return sb.toString();
    }
}
