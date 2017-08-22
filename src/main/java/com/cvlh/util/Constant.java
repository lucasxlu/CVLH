package com.cvlh.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by LucasX on 2017/1/6.
 */
public class Constant {

    public static final String BROWSER_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";

    public static String WORD_2_VEC_PATH = null;
    public static String PRETRAINED_MODEL_VGGFACE = null;
    public static String OPENCV_FACE_PRETRAINED_MODEL = null;
    public static String OPENCV_EYES_PRETRAINED_MODEL = null;
    public static String ATTRACTIVENESS_LABEL_EXCEL_PATH = null;
    public static String IMAGE_FACE_DIR = null;
    public static final int TIME_OUT = 10000;

    static {
        Properties properties = new Properties();
        try {
            InputStream in = Constant.class.getClassLoader().getResourceAsStream("configuration.properties");
            properties.load(in);
            WORD_2_VEC_PATH = properties.getProperty("WORD_2_VEC_PATH").toString().trim();
            PRETRAINED_MODEL_VGGFACE = properties.getProperty("PRETRAINED_MODEL_VGGFACE").toString().trim();
            OPENCV_FACE_PRETRAINED_MODEL = properties.getProperty("OPENCV_FACE_PRETRAINED_MODEL").toString().trim();
            OPENCV_EYES_PRETRAINED_MODEL = properties.getProperty("OPENCV_EYES_PRETRAINED_MODEL").toString().trim();
            ATTRACTIVENESS_LABEL_EXCEL_PATH = properties.getProperty("ATTRACTIVENESS_LABEL_EXCEL_PATH").toString().trim();
            IMAGE_FACE_DIR = properties.getProperty("IMAGE_FACE_DIR").toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}