package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.model.GovReport;
import com.cvlh.service.GovReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class GovReportController extends BaseController {

    @Autowired
    private GovReportService govReportService;

    @RequestMapping(value = "/hzau/gov", method = RequestMethod.GET)
    @ResponseBody
    public Object sentiment(HttpServletResponse httpServletResponse, String chairman) throws IOException {
        List<GovReport> govReportList = govReportService.selectByChairman(chairman);

        return renderSuccess(govReportList, httpServletResponse);
    }
}
