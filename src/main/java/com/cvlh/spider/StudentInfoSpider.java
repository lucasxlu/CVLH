package com.cvlh.spider;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * a spider to crawl students' personal info
 * Created by 29140 on 2017/1/6.
 */
public class StudentInfoSpider {

    private static final String LOGIN_URL = "http://211.69.132.96/";

    public void login() {

    }

    /**
     * break captcha
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
