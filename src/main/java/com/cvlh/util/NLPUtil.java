package com.cvlh.util;


import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.nlp.ESimnetType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * Created by 29140 on 2017/3/8.
 */
public class NLPUtil {

    private static final Logger logger = LogManager.getLogger();

    /**
     * extract topics from comment
     *
     * @param aipNlp
     * @param comment
     * @param eSimnetType
     */
    public static JSONObject commentExtract(AipNlp aipNlp, String comment, ESimnetType eSimnetType) {
        // get emotion attributes
        JSONObject response = aipNlp.commentTag(comment, eSimnetType);

        return response;
    }

    /**
     * initialize a AipNlp client
     *
     * @param appId
     * @param aipKey
     * @param aipToken
     * @return
     */
    private static AipNlp initClient(String appId, String aipKey, String aipToken) {
        // initialize a NLPClient
        AipNlp aipNlp = new AipNlp(appId, aipKey, aipToken);
        // options:se params
        aipNlp.setConnectionTimeoutInMillis(2000);
        aipNlp.setSocketTimeoutInMillis(60000);

        return aipNlp;
    }

    public static void main(String[] args) {
        AipNlp aipNlp = initClient("9368992", "QLwZ0GxVsmiHvfGdHuc3S54M", "MuzMNEmLgNGpIGCjUhnAP4NkPbimcsX7");
        JSONObject jsonObject = commentExtract(aipNlp, "这家店子真难吃，又贵又难吃，服务员态度好差。环境倒还可以", ESimnetType.FOOD);

        logger.debug(jsonObject.toString());

    }

}