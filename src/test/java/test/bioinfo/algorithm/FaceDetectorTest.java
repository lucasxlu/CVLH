package test.bioinfo.algorithm;

import org.junit.Test;

import java.text.NumberFormat;

public class FaceDetectorTest {

    public static double rectifiedSigmoid(double x) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        double value = (1 / (1 + Math.pow(Math.E, -20 * (x - 0.75))));

        return Double.valueOf(nf.format(value * 100));
    }

    @Test
    public void testSigmoid() {
        System.out.println(rectifiedSigmoid(0.85));
    }
}
