package com.cvlh.util;

/**
 * Created by 29140 on 2017/1/20.
 */
public class MathUtil {

    /**
     * return the formatted number
     * <b>
     * Exp:
     * if the number(student ID) is 1~9, then the result will be "XXXX01" ~ "XXXX09";
     * if the number(student ID) >= 10, then the result will be number in String format.
     * </b>
     *
     * @param number
     * @return
     */
    public static String formatNumber(int number) {
        return number < 10 ? "0" + number : String.valueOf(number);
    }
}
