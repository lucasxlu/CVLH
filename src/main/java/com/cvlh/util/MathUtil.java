package com.cvlh.util;

/**
 * Created by 29140 on 2017/1/20.
 */
@SuppressWarnings("ALL")
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

    /**
     * calculate the cosine similarity of two feature vectors
     *
     * @param featureVector1
     * @param featureVector2
     * @return
     */
    public static double cosineSimilarity(float[] featureVector1,
                                          float[] featureVector2) {
        double numerator = 0.0d;
        for (int i = 0; i < featureVector2.length; i++) {
            numerator += featureVector1[i] * featureVector2[i];
        }

        double temp1 = 0.0d, temp2 = 0.0d;
        for (int i = 0; i < featureVector1.length; i++) {
            temp1 += Math.pow(featureVector1[i], 2);
        }
        for (int i = 0; i < featureVector2.length; i++) {
            temp2 += Math.pow(featureVector2[i], 2);
        }
        double denominator = Math.sqrt(temp1) * Math.sqrt(temp2);

        return numerator / denominator;
    }

    /**
     * calculate the cosine similarity of two feature vectors
     *
     * @param featureVector1
     * @param featureVector2
     * @return
     */
    public static double cosineSimilarity(double[] featureVector1,
                                          double[] featureVector2) {
        double numerator = 0.0d;
        for (int i = 0; i < featureVector2.length; i++) {
            numerator += featureVector1[i] * featureVector2[i];
        }

        double temp1 = 0.0d, temp2 = 0.0d;
        for (int i = 0; i < featureVector1.length; i++) {
            temp1 += Math.pow(featureVector1[i], 2);
        }
        for (int i = 0; i < featureVector2.length; i++) {
            temp2 += Math.pow(featureVector2[i], 2);
        }
        double denominator = Math.sqrt(temp1) * Math.sqrt(temp2);

        return numerator / denominator;
    }

    public static String[] getUrlId(int power) {
        String[] ids = new String[(int) Math.pow(10, power)];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = MathUtil.formatNum(i);
        }

        return ids;
    }
}
