package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.model.HZAUFace;
import com.cvlh.service.HZAUFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 29140 on 2017/4/11.
 */
@Controller
public class HZAUFaceController extends BaseController {

    @Autowired
    private HZAUFaceService hzauFaceService;

    @RequestMapping(value = "/hzau/face", method = RequestMethod.GET)
    @ResponseBody
    public Object showByTime(HttpServletResponse httpServletResponse) {
        List<HZAUFace> hzauFaceList = hzauFaceService.showAllFaces();

        return null != hzauFaceList ? renderSuccess(hzauFaceList, httpServletResponse) : renderError("No Data!", httpServletResponse);
    }

    @RequestMapping(value = "/hzau/scoreface", method = RequestMethod.POST)
    @ResponseBody
    public Object scoreFace(@RequestParam String idnumber, @RequestParam double labeledScore, @RequestParam String labeler, HttpServletResponse httpServletResponse) {
        int i = hzauFaceService.score(idnumber, labeledScore, labeler);

        return i != 0 ? renderSuccess("face scoring done!", httpServletResponse) : renderError("ERROR!", httpServletResponse);
    }

    @RequestMapping(value = "/hzau/showface", method = RequestMethod.POST)
    @ResponseBody
    public Object showFace(@RequestParam double minVal, @RequestParam double maxVal, HttpServletResponse httpServletResponse) {
        List<HZAUFace> hzauFaceList = hzauFaceService.showFacesByRange(minVal, maxVal);

        return null != hzauFaceList ? renderSuccess(hzauFaceList, httpServletResponse) : renderError("No Data!", httpServletResponse);
    }
}
