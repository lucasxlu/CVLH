package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.model.douban.TimeSequence;
import com.cvlh.service.DoubanItemCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 29140 on 2017/2/25.
 */
@Controller
public class DoubanItemCommentController extends BaseController {

    @Autowired
    private DoubanItemCommentService doubanItemCommentService;

    @RequestMapping(value = "/douban/time", method = RequestMethod.GET)
    @ResponseBody
    public Object showByTime(HttpServletResponse httpServletResponse) {
        List<TimeSequence> dateList = doubanItemCommentService.showCommentsTimeSeq();
        return null != dateList ? renderSuccess(dateList, httpServletResponse) : renderError("No Data!", httpServletResponse);
    }

}
