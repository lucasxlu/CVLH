package com.cvlh.util;

import java.util.HashMap;
import java.util.Map;

/**
 * a toolbox for machine learning
 */
public class MLUtil {

    public static Map classifyImage(String imagePath) {
//        do something
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> precision = new HashMap<>();
        precision.put("human", 0.841);
        precision.put("cat", 0.021);
        precision.put("car", 0.261);
        precision.put("dog", 0.151);
        precision.put("bike", 0.001);
        result.put("precision", precision);

        HashMap<String, Object> recall = new HashMap<>();
        recall.put("human", 0.841);
        recall.put("cat", 0.021);
        recall.put("car", 0.261);
        recall.put("dog", 0.151);
        recall.put("bike", 0.001);
        result.put("recall", recall);

        return result;
    }
}
