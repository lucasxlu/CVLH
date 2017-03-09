package com.cvlh.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 29140 on 2017/3/8.
 */
@Deprecated
public class NLP implements NLPService {

    private static final Logger logger = LogManager.getLogger();

    /**
     * extract info from response json object
     *
     * @param token
     * @param comment
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void extractComment(String token, String comment) throws URISyntaxException, IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        URI uri = new URIBuilder().setScheme("https").setHost("aip.baidubce.com").setPath("/rpc/2.0/nlp/v1/comment_tag")
                .setParameter("access_token", token).build();
        HttpPost httpPost = new HttpPost(uri);

        httpPost.setEntity(new StringEntity(new NLP().commAbstractBody(comment, "11").toString()));

        httpPost.addHeader("Content-Type", "application/json");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        logger.debug(closeableHttpResponse.toString());

        logger.debug(EntityUtils.toString(httpEntity));

    }

    /**
     * get access token from baidu cloud
     *
     * @param apiKey
     * @param secretKey
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String getToken(String apiKey, String secretKey) throws URISyntaxException, IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        URI uri = new URIBuilder().setScheme("https").setHost("aip.baidubce.com").setPath("/oauth/2.0/token").setParameter("grant_type", "client_credentials")
                .setParameter("client_id", apiKey).setParameter("client_secret", secretKey).build();
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpEntity));

        return jsonObject.get("access_token").toString();
    }


    @Override
    public Object commAbstractBody(String comment, String typeId) {
        /*List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("comment", comment));
        params.add(new BasicNameValuePair("entity", "NULL"));
        params.add(new BasicNameValuePair("type", "10"));

        return params;*/
        Map<String, String> map = new HashMap<>();
        map.put("comment", comment);
        map.put("entity", "NULL");
        map.put("type", typeId);
        return JSONObject.wrap(map);

    }

    public static void main(String[] args) {
        try {
            String token = getToken("QLwZ0GxVsmiHvfGdHuc3S54M", "MuzMNEmLgNGpIGCjUhnAP4NkPbimcsX7");
            logger.info(token);
            extractComment(token, "个人觉得福克斯好，外观漂亮年轻，动力和操控性都不错");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
