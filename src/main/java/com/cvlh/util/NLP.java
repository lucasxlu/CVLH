package com.cvlh.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by 29140 on 2017/3/8.
 */
public class NLP {

    private static final Logger logger = LogManager.getLogger();

    public static void extractComment(String comment) throws URISyntaxException {


    }

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


    public static void main(String[] args) {
        try {
            String token = getToken("QLwZ0GxVsmiHvfGdHuc3S54M", "MuzMNEmLgNGpIGCjUhnAP4NkPbimcsX7");
            logger.info(token);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
