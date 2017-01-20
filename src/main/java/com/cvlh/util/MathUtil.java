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

    /**
     * return the formatted number
     *
     * @param num
     * @return
     */
    @Deprecated
    public static String formatNum(int num) {
        int len = String.valueOf(num).length();
        String formattedNum = null;
        switch (len) {
            case 1:
                formattedNum = "00000" + num;
                break;
            case 2:
                formattedNum = "0000" + num;
                break;
            case 3:
                formattedNum = "000" + num;
                break;
            case 4:
                formattedNum = "00" + num;
                break;
            case 5:
                formattedNum = "0" + num;
                break;
            case 6:
                formattedNum = "" + num;
                break;
        }

        return formattedNum;
    }

    public static String[] getUrlId(int power) {
        String[] ids = new String[(int) Math.pow(10, power)];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = MathUtil.formatNum(i);
        }

        return ids;
    }
}
