package com.cvlh.spider;

import com.cvlh.util.Constant;
import com.cvlh.util.MathUtil;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * a spider to crawl students' personal info
 * Created by 29140 on 2017/1/6.
 */
public class StudentInfoSpider {

    private static final String LOGIN_URL = "http://211.69.132.96/";
    private static final String AVATAR_URL_PREFIX = "http://211.69.132.96/Uploadfiles/UserPhoto/PostGraduate/";
    private static final Logger logger = LogManager.getLogger();
    private static final int[] loginBtnSize = new int[]{87, 23};

    public void login(String username, String password, String captcha) throws IOException {
        HttpPost httpPost = new HttpPost(LOGIN_URL);
        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Host", "211.69.132.96");
        httpPost.addHeader("Origin", StudentInfoSpider.LOGIN_URL);
        httpPost.addHeader("Referer", StudentInfoSpider.LOGIN_URL);
        httpPost.addHeader("Upgrade-Insecure-Requests", "1");
        httpPost.addHeader("User-Agent", Constant.BROWSER_USER_AGENT);

        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("Flag", "Login"));
        formParams.add(new BasicNameValuePair("username", username));
        formParams.add(new BasicNameValuePair("password", password));

//        ddlUserClass means your identity
        formParams.add(new BasicNameValuePair("ddlUserClass", "1"));
        formParams.add(new BasicNameValuePair("code1", String.valueOf(captcha)));

//        ctl00 means the location of your mouse when you hit LOGIN BUTTON
        formParams.add(new BasicNameValuePair("ctl00.x", String.valueOf(loginBtnSize[0] - (int) (Math.random() * 10))));
        formParams.add(new BasicNameValuePair("ctl00.y", String.valueOf(loginBtnSize[1] - (int) (Math.random() * 5))));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            /*closeableHttpResponse.headerIterator().forEachRemaining(o -> {
                System.out.println(o.toString());
            });*/

            String cookie = "varPartWitMis_ChoiceCourseAdd.aspx=10; " + closeableHttpResponse.getFirstHeader("Set-Cookie").getValue().split(";")[0];
            System.out.println(cookie);
            CloseableHttpClient closeableHttpClient1 = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(StudentInfoSpider.LOGIN_URL + "admin.aspx?type=Login");
            httpGet.addHeader("Cookie", cookie);

            CloseableHttpResponse closeableHttpResponse1 = closeableHttpClient1.execute(httpGet);
            System.out.println(EntityUtils.toString(closeableHttpResponse1.getEntity()));

        } else if (closeableHttpResponse.getStatusLine().getStatusCode() == 403) {
            logger.error("Login error!");
        }

    }

    /**
     * break captcha
     *
     * @param captchaImgPath
     * @return
     * @throws TesseractException
     */
    public String deCaptcha(String captchaImgPath) throws TesseractException {
        File file = new File(captchaImgPath);
        ITesseract iTesseract = new Tesseract();

        String captcha = iTesseract.doOCR(file);

        return captcha.trim();
    }

    /**
     * convert a aspx file to a <b>png</b> file
     *
     * @param aspxPath
     * @return
     */
    @Deprecated
    public String aspxToPng(String aspxPath) throws IOException {
        File pngFile = null;
        if (aspxPath.endsWith(".aspx")) {
            byte[] bytes = Files.readAllBytes(Paths.get(aspxPath));
            pngFile = new File(aspxPath.replace(".aspx", ".png"));
            FileUtils.writeByteArrayToFile(pngFile, bytes);
            Files.delete(Paths.get(aspxPath));
        } else {
            logger.error("Invalid image format of captcha!");
        }

        return pngFile.getAbsolutePath();
    }

    /**
     * first hit and return captcha image in <b>JPG</b>
     *
     * @return
     */
    public String getJPGImg() throws IOException {
        File file = new File("Image.jpg");
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(StudentInfoSpider.LOGIN_URL);
        httpGet.addHeader("User-Agent", Constant.BROWSER_USER_AGENT);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (200 == closeableHttpResponse.getStatusLine().getStatusCode()) {
            FileUtils.copyURLToFile(new URL(StudentInfoSpider.LOGIN_URL + "Image.aspx"), file);
        } else if (413 == closeableHttpResponse.getStatusLine().getStatusCode()) {
            logger.error("Request forbidden!");
        } else {
            logger.debug(closeableHttpResponse.getStatusLine());
        }

        return file.getAbsolutePath();
    }


    /**
     * @param year
     * @param collegeId
     * @param studentId
     * @param directory the directory to save all images
     * @throws IOException
     * @Note: if the user did not upload his or her avatar, the default avatar is <b>malem.png</b> for male, and <b>femalem.png</b> for female
     */
    public void downloadAvatar(int year, int collegeId, String studentId, String directory) throws IOException {
        if (!Files.exists(Paths.get(directory))) {
            Files.createDirectories(Paths.get(directory));
        }

        String requestUrl = StudentInfoSpider.AVATAR_URL_PREFIX + "_" + String.valueOf(year) + String.valueOf(collegeId) + "1100" + studentId + ".jpg";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestUrl);
        logger.debug("The URL is " + requestUrl);
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.addHeader("User-Agent", Constant.BROWSER_USER_AGENT);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (200 == closeableHttpResponse.getStatusLine().getStatusCode()) {
            FileUtils.copyURLToFile(new URL(requestUrl), new File(directory + String.valueOf(year + "" + collegeId + "1100" + studentId + ".jpg")));
        } else if (413 == closeableHttpResponse.getStatusLine().getStatusCode()) {
            logger.error("Request forbidden!");
        } else {
            logger.debug(closeableHttpResponse.getStatusLine());
        }

    }

    public static void main(String[] args) {
        StudentInfoSpider studentInfoSpider = new StudentInfoSpider();
        try {
            String captcha = studentInfoSpider.deCaptcha(studentInfoSpider.getJPGImg());
            logger.info("captcha is " + captcha);
//            studentInfoSpider.login("2016317110028", "19930620", captcha);
            for (int i = 1; i < 60; i++) {
                studentInfoSpider.downloadAvatar(2016, 317, MathUtil.formatNumber(i), "D:/hzau/");
            }
        } catch (TesseractException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
