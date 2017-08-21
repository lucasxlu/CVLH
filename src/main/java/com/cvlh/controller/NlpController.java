package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.util.NlpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NlpController extends BaseController {

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
}
