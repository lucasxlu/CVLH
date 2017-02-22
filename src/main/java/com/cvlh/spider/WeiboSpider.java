package com.cvlh.spider;

import com.cvlh.util.Constant;
import com.cvlh.util.SpiderUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
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
import java.nio.file.Files;
import java.nio.file.Paths;

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
            Files.readAllLines(Paths.get("D:\\Users\\IdeaProjects\\TempProjects\\CVLH-BE\\src\\main\\resources\\cookie.txt")).forEach(s -> {
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

    public static void main(String[] args) {
//        String weiboUrl = "https://weibo.cn/comment/EvsBXBh9D?page=";
        try {
            new WeiboSpider().crawlWeiboComments("https://weibo.cn/comment/EvsBXBh9D?page=1");
        } catch (InterruptedException e) {
            logger.error("craw this page fails!!");
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
