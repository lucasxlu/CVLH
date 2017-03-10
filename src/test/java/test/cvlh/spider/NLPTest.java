package test.cvlh.spider;

import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.nlp.ESimnetType;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by 29140 on 2017/3/9.
 */
public class NLPTest {

    @Test
    public void testNLP() {
        // 初始化一个NLPClient
        AipNlp client = new AipNlp("9368992", "QLwZ0GxVsmiHvfGdHuc3S54M", "MuzMNEmLgNGpIGCjUhnAP4NkPbimcsX7");
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 获取美食评论情感属性
        JSONObject response = client.commentTag("这家餐馆味道不错", ESimnetType.FOOD);
        System.out.println(response.toString());

    }
}
