package com.cvlh.spider;

import com.cvlh.util.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by 29140 on 2017/2/21.
 * <p>
 * a spider for sina Weibo
 */
public class WeiboSpider {

    private static final Logger logger = LogManager.getLogger();

    /**
     * crawl all weibo comments belong to a specific weibo
     *
     * @param weiboUrl
     */
    public void crawlWeiboComments(String weiboUrl) throws InterruptedException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        // Create a local instance of cookie store
        CookieStore cookieStore = new BasicCookieStore();
        // Populate cookies if needed
        BasicClientCookie cookie1 = new BasicClientCookie("_T_WM", "f45c6eee5166f31d973de90975ed4ac9");
        BasicClientCookie cookie2 = new BasicClientCookie("ALF", "1490280565");
        BasicClientCookie cookie3 = new BasicClientCookie("SCF", "AhXK3tD8oBIXxzVIoCDOKw9bQ7kc7UkM5pk7bRbkA27ccD6uXdzsLxUjAc5UPLfCDwyRU4LQ5HtFMrJ-NYrXPr0.");
        BasicClientCookie cookie4 = new BasicClientCookie("SUB", "_2A251qCPLDeRxGeRK6FUY8SfFwz-IHXVXU02DrDV6PUJbktBeLXT1kW07dABJOV-ZHvFNaVTz1V3s2ZShrQ..");
        BasicClientCookie cookie5 = new BasicClientCookie("SUBP", "0033WrSXqPxfM725Ws9jqgMF55529P9D9WFA5TwRz43RBDwVNEW_BoS35JpX5o2p5NHD95QESheN1K241Kn0Ws4DqcjMi--fi-zRiKnfi--NiKnciKyWi--NiKnciKyWK5tt");
        BasicClientCookie cookie6 = new BasicClientCookie("SUHB", "0vJJmThEzOvnCu");
        BasicClientCookie cookie7 = new BasicClientCookie("SSOLoginState", "1487688603");
        cookieStore.addCookie(cookie1);
        cookieStore.addCookie(cookie2);
        cookieStore.addCookie(cookie3);
        cookieStore.addCookie(cookie4);
        cookieStore.addCookie(cookie5);
        cookieStore.addCookie(cookie6);
        cookieStore.addCookie(cookie7);

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).setConnectionManager(cm).build();
        String[] urisToGet = {
                "https://weibo.cn/comment/EvsBXBh9D?page=1",
                "https://weibo.cn/comment/EvsBXBh9D?page=2",
                "https://weibo.cn/comment/EvsBXBh9D?page=3",
                "https://weibo.cn/comment/EvsBXBh9D?page=4"
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
                Document document = Jsoup.parse(html);
                document.getElementsByAttributeValue("class", "c").forEach(element -> {
                    System.out.println(element.toString());
                });

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
