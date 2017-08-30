package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.service.NlpService;
import com.cvlh.util.MathUtil;
import com.cvlh.util.NlpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("Duplicates")
@Controller
public class NlpController extends BaseController {

    @Autowired
    private NlpService nlpService;

    @RequestMapping(value = "/hzau/sentiment", method = RequestMethod.POST)
    @ResponseBody
    public Object sentiment(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String text) throws IOException {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("positive", Math.random());
        hashMap.put("negative", Math.random());

        return renderSuccess(hashMap, httpServletResponse);
    }

    @RequestMapping(value = "/hzau/tokenize", method = RequestMethod.POST)
    @ResponseBody
    public Object tokenize(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String text, boolean withPosition) throws IOException {
        Map<String, Object> hashMap = new HashMap<>();
        long startTime = System.currentTimeMillis();
        List<Object> objectList = NlpUtil.tokenize(text, withPosition);
        long endTime = System.currentTimeMillis();
        hashMap.put("time", endTime - startTime);
        hashMap.put("result", objectList);

        return renderSuccess(hashMap, httpServletResponse);
    }

    @RequestMapping(value = "/hzau/tfidf", method = RequestMethod.POST)
    @ResponseBody
    public Object tokenize(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String text) throws IOException {
        Map<String, Object> hashMap = new HashMap<>();
        long startTime = System.currentTimeMillis();
        List<Object> objectList = NlpUtil.tokenize(text, false);
        List<String> stringList = new ArrayList<>();
        for (Object object : objectList)
            stringList.add(object.toString());
        long endTime = System.currentTimeMillis();
        hashMap.put("result", NlpUtil.tfIdf(NlpUtil.tf(stringList), NlpUtil.idf(stringList)));
        hashMap.put("time", endTime - startTime);

        return renderSuccess(hashMap, httpServletResponse);
    }

    @RequestMapping(value = "/hzau/word/sim", method = RequestMethod.POST)
    @ResponseBody
    public Object calcWordsSimilarity(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String word1, String word2) throws IOException {
        Map<String, Object> hashMap = new HashMap<>();
        long startTime = System.currentTimeMillis();
        hashMap.put("similarity", NlpUtil.calcWordsSimilarity(word1, word2));
        long endTime = System.currentTimeMillis();
        hashMap.put("time", endTime - startTime);

        return hashMap;
    }

    @RequestMapping(value = "/hzau/document/sim", method = RequestMethod.POST)
    @ResponseBody
    public Object calcDocsSimilarity(HttpServletResponse httpServletResponse, String doc1, String doc2) throws IOException {
        Map<String, Object> hashMap = new HashMap<>();
        long startTime = System.currentTimeMillis();
        hashMap.put("similarity", nlpService.calcDocsSimilarity(doc1, doc2));
        long endTime = System.currentTimeMillis();
        hashMap.put("time", endTime - startTime);

        return renderSuccess(hashMap, httpServletResponse);
    }
}
