package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.spider.WeiboSpider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class CrawlController extends BaseController {

    @RequestMapping(value = "/hzau/web/crawl", method = RequestMethod.POST)
    @ResponseBody
    public Object calcWordsSimilarity(String name, String url) throws IOException, InterruptedException {
        if ("douban".equalsIgnoreCase(name)) {
            System.out.println("douban crawler...");
        } else if ("weibo".equalsIgnoreCase(name)) {
            new WeiboSpider().crawlWeiboComments(url);
        }

        return null;
    }
}
