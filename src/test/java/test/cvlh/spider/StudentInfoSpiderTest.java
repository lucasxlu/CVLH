package test.cvlh.spider;

import com.cvlh.spider.StudentInfoSpider;
import org.junit.Test;

import java.io.IOException;
import java.io.InterruptedIOException;

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

    @Test
    public void testDownloadAvatar() throws IOException {
        new StudentInfoSpider().downloadAvatar(2016, 317, "317", 28 + "", "D:/");
    }

    @Test
    public void testFormat() {
        System.out.println(Integer.parseInt("02"));
    }
}
