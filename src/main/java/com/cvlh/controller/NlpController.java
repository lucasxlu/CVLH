package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

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
}
