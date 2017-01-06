package com.cvlh.spider;

import com.cvlh.util.Constant;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * a spider to crawl students' personal info
 * Created by 29140 on 2017/1/6.
 */
public class StudentInfoSpider {

    private static final String LOGIN_URL = "http://211.69.132.96/";

    public void login(String username, String password) throws IOException {
        HttpPost httpPost = new HttpPost(LOGIN_URL);
        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Host", "211.69.132.96");
        httpPost.addHeader("Origin", "http://211.69.132.96");
        httpPost.addHeader("Referer", "http://211.69.132.96/");
        httpPost.addHeader("Upgrade-Insecure-Requests", "1");
        httpPost.addHeader("User-Agent", Constant.BROWSER_USER_AGENT);

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("Flag", "Login"));
        formparams.add(new BasicNameValuePair("username", username));
        formparams.add(new BasicNameValuePair("password", password));
        formparams.add(new BasicNameValuePair("ddlUserClass", "1"));
        formparams.add(new BasicNameValuePair("code1", "1"));
        formparams.add(new BasicNameValuePair("ctl00.x", "56"));
        formparams.add(new BasicNameValuePair("ctl00.y", "15"));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            System.out.println(EntityUtils.toString(httpEntity));
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

        return captcha;
    }

    public static void main(String[] args) {
        try {
            String captcha = new StudentInfoSpider().deCaptcha("C:\\Users\\29140\\Desktop\\Image.png");
            System.out.println(captcha);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }

}
