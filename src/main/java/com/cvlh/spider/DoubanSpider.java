package com.cvlh.spider;

import com.cvlh.model.DoubanItemComment;
import com.cvlh.model.DoubanItemReview;
import com.cvlh.util.Constant;
import com.cvlh.util.FileUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by 29140 on 2017/2/20.
 * <p>
 * a spider for douban
 */
@SuppressWarnings("ALL")
public class DoubanSpider {

    private static final Logger logger = LogManager.getLogger();
    private static List<DoubanItemReview> doubanItemReviewArrayList = new ArrayList<>();
    private static List<DoubanItemComment> doubanItemCommentArrayList = new ArrayList<>();
    protected static final String DOUBAN_MOVIE = "movie";
    protected static final String DOUBAN_BOOK = "book";
    private static final String DOUBAN_ITEM_ID = "26363254";
//    private static SqlSession sqlSession;

/*    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }*/

    /**
     * a web spider for douban item <b>review</b>
     *
     * @param itemId
     */
    public void crawlDoubanItemComments(String itemId, int maxPage, String itemType) throws IOException, InterruptedException {
        CloseableHttpClient closeableHttpClient = HttpClients.custom().setUserAgent(Constant.BROWSER_USER_AGENT).build();
        for (int i = 0; i < maxPage; i++) {
            String url = "https://" + itemType + ".douban.com/subject/" + String.valueOf(itemId) + "/comments?start=" + String.valueOf(i * 20) + "&limit=20&sort=new_score&status=P";
            logger.debug("Page url is " + url);
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
                    DoubanSpider.extractComment(html);
                }
            }

            Thread.sleep((long) ((Math.random() * 10) * 1000));
            FileUtil.outDoubanCommentExcel(doubanItemCommentArrayList, "D:/");
        }

    }

    /**
     * a web spider for douban item <b>review</b>
     *
     * @param itemId
     */
    public void crawlDoubanItemReviews(String itemId, int maxPage, String itemType) throws IOException, InterruptedException {
        CloseableHttpClient closeableHttpClient = HttpClients.custom().setUserAgent(Constant.BROWSER_USER_AGENT).build();
        for (int i = 0; i < maxPage; i++) {
            String url = "https://" + itemType + ".douban.com/subject/" + String.valueOf(itemId) + "/reviews?start=" + String.valueOf(i * 20);
            logger.debug("Page url is " + url);
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
                    DoubanSpider.extractReview(html);
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
    private static void extractReview(String html) {
        Document document = Jsoup.parse(html);
        if (document.getElementsByAttributeValue("class", "review-list").size() > 0) {
            for (Element element : document.getElementsByAttributeValue("class", "review-list").get(0).children()) {
                String digest = null, commentId = null, username = null, content = null;
                int star = 0, upvote = 0, downvote = 0;
                Date commentDate = null;

                try {
                    digest = element.getElementsByTag("h3").get(0).getElementsByTag("a").get(0).text();
                    commentId = element.getElementsByTag("h3").get(0).getElementsByTag("a").get(0).attr("href")
                            .replace("https://movie.douban.com/review/", "").replace("/", "").trim();
                    username = element.getElementsByAttributeValue("class", "header-more").get(0).getElementsByTag("a").get(1)
                            .getElementsByTag("span").text();
                    star = Integer.parseInt(element.getElementsByAttributeValue("class", "header-more").get(0).getElementsByTag("span").get(1)
                            .attr("class").split(" ")[0].replace("allstar", "").trim());
                    String commentDateString = element.getElementsByAttributeValue("class", "header-more").get(0).getElementsByTag("span").get(2).text().trim();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    commentDate = format.parse(commentDateString);

                    content = element.getElementsByAttributeValue("class", "short-content").get(0).text().trim();
                    upvote = Integer.parseInt(element.getElementsByAttributeValue("class", "left").get(0).text().split("/")[0].replace("有用", "").trim());
                    downvote = Integer.parseInt(element.getElementsByAttributeValue("class", "left").get(0).text().split("/")[1].replace("没用", "").trim());

                    DoubanItemReview doubanItemReview = new DoubanItemReview(DOUBAN_ITEM_ID, commentId, username, star / 10, upvote, downvote, commentDate, content, digest);
                    logger.debug(doubanItemReview);
                    doubanItemReviewArrayList.add(doubanItemReview);

//                    sqlSession.getMapper(DoubanItemCommentMapper.class).insert(doubanItemComment);
                } catch (IndexOutOfBoundsException e) {
                    DoubanItemReview doubanItemComment = new DoubanItemReview(DOUBAN_ITEM_ID, commentId, username, star / 10, upvote, downvote, commentDate, content, digest);
                    doubanItemReviewArrayList.add(doubanItemComment);
                    logger.error("该评论已被折叠，无法获取赞数和反对数!!");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void extractComment(String html) {
        Document document = Jsoup.parse(html);
        Element commentDivEle = document.getElementById("comments");
        if (commentDivEle.childNodeSize() > 0) {
            Elements elements = commentDivEle.getElementsByAttributeValue("class", "comment-item");
            elements.forEach(element -> {
                DoubanItemComment doubanItemComment = null;
                try {
                    String commenter = element.getElementsByTag("div").first().getElementsByTag("a").first().attr("title").toString();
                    String commentUrl = element.getElementsByTag("div").first().getElementsByTag("a").first().attr("href").toString();
                    int vote = Integer.parseInt(element.select("div.comment > h3 > span.comment-vote > span").text().trim());
                    int star = Integer.parseInt(element.select("div.comment > h3 > span.comment-info > span").attr("class").trim().split(" ")[0]
                            .replace("allstar", "")) / 10;
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String date = element.select("div.comment > h3 > span.comment-info > span:last-child").attr("title").toString().trim();
                    Date commentTime = null;
                    try {
                        commentTime = format.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String comment = element.select("div.comment > p").text().trim();
                    doubanItemComment = new DoubanItemComment(DOUBAN_ITEM_ID, commenter, commentUrl, vote, star, commentTime, comment);
                    logger.debug(doubanItemComment);
                } catch (Exception e) {
                    e.printStackTrace();
                    doubanItemComment = null;
                }
                if (null != doubanItemComment)
                    doubanItemCommentArrayList.add(doubanItemComment);
            });
        } else {
            logger.warn("This page has no comments!!");
        }

    }

    public static void main(String[] args) {
        try {
            new DoubanSpider().crawlDoubanItemComments(DOUBAN_ITEM_ID, 2300, DOUBAN_MOVIE);
            logger.info("The size of comment list is " + doubanItemCommentArrayList.size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
