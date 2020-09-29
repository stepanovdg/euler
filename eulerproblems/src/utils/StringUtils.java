package utils;

/**
 * Created by Dmitriy Stepanov on 10/18/17.
 */
public class StringUtils {

    public static boolean isPalindrom(String str) {
        int l = str.length();
        if (l == 1 || l == 0) {
            return true;
        }
        boolean b = str.charAt(0) == str.charAt(l - 1);
        if (l == 2) {
            return b;
        } else if (b) {
            return isPalindrom(str.substring(1, l - 1));

        } else {
            return false;
        }
    }
}
