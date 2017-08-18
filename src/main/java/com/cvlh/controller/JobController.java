package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.entity.NlpJob;
import com.cvlh.spider.NlpJobSpider;
import com.cvlh.spider.NlpJobType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class JobController extends BaseController {

    @RequestMapping(value = "/hzau/job/intern", method = RequestMethod.GET)
    @ResponseBody
    public Object imageClassify(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException {
        List<NlpJob> nlpJobList = new NlpJobSpider().crawlJobInfo(NlpJobType.SHIXI.getType());

        return renderSuccess(nlpJobList, httpServletResponse);
    }
}
