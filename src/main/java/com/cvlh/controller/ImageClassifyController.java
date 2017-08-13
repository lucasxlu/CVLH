package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.util.MLUtil;
import com.cvlh.util.UploadUtil;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
public class ImageClassifyController extends BaseController {

    @RequestMapping(value = "/hzau/image/classify", method = RequestMethod.POST)
    @ResponseBody
    public Object imageClassify(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, @RequestParam("file") CommonsMultipartFile[] files) {
        String a = files[0].getOriginalFilename();
        List<String> pathList = null;
        HashMap<String, Object> result = new HashMap();
        if (a == null || a.equals("")) {
        } else {
            pathList = UploadUtil.batchUpload(httpServletRequest, files, "head");
            String relPath = pathList.get(0); //relative path
            String absPath = pathList.get(1); //absolute path
            Long startTime = System.currentTimeMillis();
            HashMap<String, Object> hashMap = (HashMap<String, Object>) MLUtil.classifyImage(absPath);
            result.put("result", hashMap);
            Long endTime = System.currentTimeMillis();
            result.put("imgSrc", relPath);
            result.put("time", (endTime - startTime) / 1000);
        }


        return renderSuccess(result, httpServletResponse);
    }


}
