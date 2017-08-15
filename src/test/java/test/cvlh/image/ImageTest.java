package test.cvlh.image;

import com.cvlh.controller.ImageController;
import org.junit.Test;

public class ImageTest {

    @Test
    public void testDetectFace() {
        System.out.println(new ImageController().detectFaces(null, null, "D:\\TestFace\\1.jpg"));
    }
}
