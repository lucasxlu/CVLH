package com.cvlh.spider;

import com.alibaba.fastjson.JSON;
import com.cvlh.util.Constant;
import com.cvlh.util.SpiderUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by 29140 on 2017/2/21.
 * <p>
 * a spider for sina Weibo
 */
public class WeiboSpider {

    private static final Logger logger = LogManager.getLogger();
    private static String cookieString = null;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.readAllLines(Paths.get("D:\\Users\\IdeaProjects\\TempProjects\\CVLH\\src\\main\\resources\\cookie.txt")).forEach(s -> {
                stringBuilder.append(s);
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cookieString = stringBuilder.toString();
        }
    }

    /**
     * crawl all weibo comments belong to a specific weibo
     *
     * @param weiboUrl
     */
    public void crawlWeiboComments(String weiboUrl) throws InterruptedException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        CookieStore cookieStore = SpiderUtil.normalizeCookie(cookieString, "weibo.cn");

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).setConnectionManager(cm).build();
        String[] urisToGet = {
                "https://weibo.cn/comment/EvsBXBh9D?page=1"
        };

        // create a thread for each URI
        GetThread[] threads = new GetThread[urisToGet.length];
        for (int i = 0; i < threads.length; i++) {
            HttpGet httpGet = new HttpGet(urisToGet[i]);
            httpGet.addHeader("User-Agent", Constant.BROWSER_USER_AGENT);
            httpGet.addHeader("Upgrade-Insecure-Requests", "1");
            httpGet.addHeader("Referer", "https://weibo.cn/comment/EvsBXBh9D?page=1");
            httpGet.addHeader("Host", "weibo.cn");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            threads[i] = new GetThread(httpClient, httpGet);
        }

        // start the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }

        // join the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }
    }

    /**
     * crawl all weibo belong to the same topic
     *
     * @param topic
     */
    public static void crawlTopicWeibo(String topic) throws IOException, URISyntaxException {
        CookieStore cookieStore = SpiderUtil.normalizeCookie(cookieString, "weibo.cn");
        HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).setUserAgent(Constant.BROWSER_USER_AGENT).build();

        HashMap<String, String> sinceId = new HashMap<>();
        sinceId.put("last_since_id", "4180243927739050");
        sinceId.put("res_type", "1");
        sinceId.put("next_since_id", "4180162428354000");

        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("m.weibo.cn")
                .setPath("/api/container/getIndex")
                .setParameter("containerid", "10080850111ec45d2a9e4140a996ca9f1b7b85")
                .setParameter("extparam", topic.trim())
                .setParameter("luicode", "10000011")
                .setParameter("lfid", "100803")
                .setParameter("since_id", sinceId.toString())
                .build();
        HttpGet httpGet = new HttpGet(uri);

        System.out.println(uri.toString());

        httpGet.setHeader("Content-Type", "application/json, text/plain, */*");
        httpGet.setHeader("Host", "m.weibo.cn");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
        httpGet.setHeader("Referer", "https://m.weibo.cn");

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();

        JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(httpEntity));
        JSONObject pageInfo = (JSONObject) jsonObject.get("pageInfo");
        System.out.println(pageInfo.get("desc"));
    }

    public static void main(String[] args) {
        try {
            crawlTopicWeibo("双12");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    protected static void extractInfoFromHtml(String html) {
        Document document = Jsoup.parse(html);
        document.getElementsByAttributeValue("class", "c").forEach(element -> {
            String username = element.getElementsByTag("a").text();
            System.out.println(username);
        });
    }
}

class GetThread extends Thread {

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpGet;

    public GetThread(CloseableHttpClient httpClient, HttpGet httpGet) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpGet = httpGet;
    }

    @Override
    public void run() {
        try {
            CloseableHttpResponse response = httpClient.execute(
                    httpGet, context);
            try {
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity);
                WeiboSpider.extractInfoFromHtml(html);

            } finally {
                response.close();
            }
        } catch (ClientProtocolException ex) {
            // Handle protocol errors
        } catch (IOException ex) {
            // Handle I/O errors
        }
    }

}
