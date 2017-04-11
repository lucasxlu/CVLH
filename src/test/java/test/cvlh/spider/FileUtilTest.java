package test.cvlh.spider;

import com.cvlh.util.FileUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by 29140 on 2017/4/11.
 */
public class FileUtilTest {
    @Test
    public void testLoadImgToDb() {
        try {
            FileUtil.loadImgToDb("D:\\DataSet\\hzau");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
