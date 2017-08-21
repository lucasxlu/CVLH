package com.cvlh.util;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NlpUtil {

    private static final String[] PUNCTUATION = new String[]{"，", "。", "！", "？", "\"", "~", "‘", "’", "、", ",", "."};

    public static List<Object> tokenize(String text, boolean withPosition) {
        for (String punctuation : PUNCTUATION)
            text = text.replace(punctuation, "");
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokenList = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);
        List<Object> result = new ArrayList<>();
        for (SegToken segToken : segTokenList) {
            if (false == withPosition) {
                result.add(segToken.word);
            } else {
                result.add(segToken);
            }
        }

        return result;
    }

    /**
     * calculate term frequency
     *
     * @param words
     * @return
     * @version 1.0
     */
    public static Map<String, Double> tf(List<String> words) {
        Map<String, Double> hashMap = new HashMap<>();
        List<String> visitedWords = new ArrayList<>();
        for (String word : words) {
            if (visitedWords.contains(word)) {
                hashMap.put(word, hashMap.get(word) + 1 / words.size());
            } else {
                hashMap.put(word, (1.0D / words.size() + 0.0D));
            }
            visitedWords.add(word);
        }

        return hashMap;
    }

    /**
     * calculate inverse document frequency
     *
     * @param words
     * @return
     * @version 1.0
     */
    public static Map<String, Double> idf(List<String> words) {
        Map<String, Double> hashMap = new HashMap<>();
        List<String> visitedWords = new ArrayList<>();

        for (String word : words) {
            if (!visitedWords.contains(word)) {
                int count = 0;
                for (String w : words) {
                    if (w.equals(word))
                        count++;
                }
                hashMap.put(word, Math.log(words.size() / (1.0D + count)));
                visitedWords.add(word);
            }
        }

        return hashMap;
    }

    /**
     * calculate TF-IDF
     *
     * @param tf
     * @param idf
     * @return
     * @version 1.0
     */
    public static Map<String, Double> tfIdf(Map<String, Double> tf, Map<String, Double> idf) {
        Map<String, Double> hashMap = new HashMap<>();
        tf.forEach((s, aDouble) -> {
            hashMap.put(s, aDouble * idf.get(s));
        });

        return hashMap;
    }

}
