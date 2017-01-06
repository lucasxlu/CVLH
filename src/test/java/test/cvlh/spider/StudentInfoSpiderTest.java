package test.cvlh.spider;

import com.cvlh.spider.StudentInfoSpider;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by 29140 on 2017/1/6.
 */
public class StudentInfoSpiderTest {

    @Test
    public void testAspxToPng() throws IOException {
        String pngPath = new StudentInfoSpider().aspxToPng("C:\\Users\\29140\\Desktop\\Image.aspx");
    }

    @Test
    public void testGetJPGImg() throws IOException {
        String pngPath = new StudentInfoSpider().getJPGImg();
        System.out.println(pngPath);
    }

}
