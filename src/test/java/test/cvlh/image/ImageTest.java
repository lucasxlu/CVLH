package test.cvlh.image;

import com.cvlh.controller.ImageController;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageTest {

    @Test
    public void testDetectFace() {
        System.out.println(new ImageController().detectFaces(null, null, "D:\\TestFace\\1.jpg"));
    }

    public static void main(String[] args) {
        // Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Reading the Image from the file and storing it in to a Matrix object
        String file = "D:/1.jpg";
        Mat src = Imgcodecs.imread(file, Imgcodecs.IMREAD_COLOR);

        // Creating an empty matrix to store the result
        Mat dst = new Mat();

        // Applying color map to an image
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2HSV);


        System.out.println(dst);
        // Writing the image
        Imgcodecs.imwrite("D:/1-1.jpg", dst);
        System.out.println("Image processed");
    }
}
