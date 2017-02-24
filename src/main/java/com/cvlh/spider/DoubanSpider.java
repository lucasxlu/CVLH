package com.cvlh.spider;

import com.cvlh.mapper.DoubanItemCommentMapper;
import com.cvlh.model.DoubanItemComment;
import com.cvlh.util.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by 29140 on 2017/2/20.
 * <p>
 * a spider for douban
 */
public class DoubanSpider {

    private static final Logger logger = LogManager.getLogger();
    public static SqlSession sqlSession;


    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * a web spider for douban item
     *
     * @param itemId
     */
    public void crawlDoubanItemComments(int itemId, int maxPage) throws IOException, InterruptedException {
        CloseableHttpClient closeableHttpClient = HttpClients.custom().setUserAgent(Constant.BROWSER_USER_AGENT).build();
        for (int i = 0; i < maxPage; i++) {
            String url = "https://movie.douban.com/subject/" + String.valueOf(itemId) + "/reviews?start=" + String.valueOf(i * 20);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch, br");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("Host", "movie.douban.com");
            httpGet.addHeader("Referer", url);
            httpGet.addHeader("Upgrade-Insecure-Requests", "1");
            httpGet.addHeader("User-Agent", Constant.BROWSER_USER_AGENT);

            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            if (403 == closeableHttpResponse.getStatusLine().getStatusCode()) {
                logger.error("No aceess of this page : " + url);
            } else if (200 == closeableHttpResponse.getStatusLine().getStatusCode()) {
                HttpEntity httpEntity = closeableHttpResponse.getEntity();
                if (null != httpEntity) {
                    String html = EntityUtils.toString(httpEntity);
                    DoubanSpider.extractInfo(html);
                }
            }

            Thread.sleep((long) ((Math.random() * 10) * 1000));
        }

    }

    /**
     * extract info from html with JSoup
     *
     * @param html
     */
    private static void extractInfo(String html) {
        Document document = Jsoup.parse(html);
        if (document.getElementsByAttributeValue("class", "review-list").size() > 0) {
            for (Element element : document.getElementsByAttributeValue("class", "review-list").get(0).children()) {
                try {
                    String digest = element.getElementsByTag("h3").get(0).getElementsByTag("a").get(0).text();
                    String commentId = element.getElementsByTag("h3").get(0).getElementsByTag("a").get(0).attr("href")
                            .replace("https://movie.douban.com/review/", "").replace("/", "").trim();
                    String username = element.getElementsByAttributeValue("class", "header-more").get(0).getElementsByTag("a").get(1)
                            .getElementsByTag("span").text();
                    int star = Integer.parseInt(element.getElementsByAttributeValue("class", "header-more").get(0).getElementsByTag("span").get(1)
                            .attr("class").split(" ")[0].replace("allstar", "").trim());
                    String commentDateString = element.getElementsByAttributeValue("class", "header-more").get(0).getElementsByTag("span").get(2).text().trim();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date commentDate = format.parse(commentDateString);

                    String content = element.getElementsByAttributeValue("class", "short-content").get(0).text().trim();
                    int upvote = Integer.parseInt(element.getElementsByAttributeValue("class", "left").get(0).text().split("/")[0].replace("有用", "").trim());
                    int downvote = Integer.parseInt(element.getElementsByAttributeValue("class", "left").get(0).text().split("/")[1].replace("没用", "").trim());

                    DoubanItemComment doubanItemComment = new DoubanItemComment(commentId, username, star, upvote, downvote, commentDate, null, digest);
                    logger.debug(doubanItemComment);

                    sqlSession.getMapper(DoubanItemCommentMapper.class).insert(doubanItemComment);


                } catch (IndexOutOfBoundsException e) {
                    logger.error("该评论已被折叠，无法获取赞数和反对数!!");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new DoubanSpider().crawlDoubanItemComments(25975243, 80);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
