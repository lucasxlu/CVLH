package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.spider.WeiboSpider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class CrawlController extends BaseController {

    @RequestMapping(value = "/hzau/web/crawl", method = RequestMethod.POST)
    @ResponseBody
    public Object calcWordsSimilarity(String name, String topic) throws IOException, InterruptedException {
        if ("douban".equalsIgnoreCase(name)) {
            System.out.println("douban crawler...");
        } else if ("weibo".equalsIgnoreCase(name)) {
            try {
                WeiboSpider.crawlTopicWeibo(topic);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
