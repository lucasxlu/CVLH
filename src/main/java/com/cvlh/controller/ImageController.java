package com.cvlh.controller;

import com.cvlh.commons.base.BaseController;
import com.cvlh.entity.Face;
import com.cvlh.util.ImageUtil;
import com.cvlh.util.MLUtil;
import com.cvlh.util.UploadUtil;
import org.apache.commons.io.FileUtils;
import org.opencv.core.Rect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class ImageController extends BaseController {

    @RequestMapping(value = "/hzau/image/classify", method = RequestMethod.POST)
    @ResponseBody
    public Object imageClassify(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, @RequestParam("file") CommonsMultipartFile[] files) throws IOException {
        String a = files[0].getOriginalFilename();
        List<String> pathList;
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

    @RequestMapping(value = "/hzau/image/url", method = RequestMethod.POST)
    @ResponseBody
    public Object imageUrlClassify(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String url) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String serverPath = httpServletRequest.getSession().getServletContext().getRealPath("/");
        serverPath = serverPath.substring(0, serverPath.lastIndexOf(File.separator));
        serverPath = serverPath.substring(0, serverPath.lastIndexOf(File.separator));
        serverPath = serverPath + "/fileUpload/head/" + sdf.format(new Date()) + "/";
        HashMap<String, Object> result = new HashMap();
        String fileName = url.trim().split("/")[url.trim().split("/").length - 1];

        try {
            FileUtils.copyURLToFile(new URL(url), new File(serverPath + fileName));
            Long startTime = System.currentTimeMillis();
            HashMap<String, Object> hashMap = (HashMap<String, Object>) MLUtil.classifyImage(serverPath + fileName);
            result.put("result", hashMap);
            Long endTime = System.currentTimeMillis();
            result.put("imgSrc", "/fileUpload/head/" + sdf.format(new Date()) + "/" + fileName);
            result.put("time", (endTime - startTime) / 1000);

            return renderSuccess(result, httpServletResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return renderError("Invalid image URL", httpServletResponse);
        }
    }

    @RequestMapping(value = "/hzau/face/compare", method = RequestMethod.POST)
    @ResponseBody
    public Object compareFace(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String faceImg1, String faceImg2) {
        HashMap<Double, String> result = ImageUtil.faceCompare(faceImg1, faceImg2);

        return renderSuccess(result, httpServletResponse);
    }

    @RequestMapping(value = "/hzau/face/detect", method = RequestMethod.POST)
    @ResponseBody
    public Object detectFaces(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, String faceImgPath) {
        long startTime = System.currentTimeMillis();
        HashMap<Integer, Rect[]> map = ImageUtil.detectFaceCoordinate(faceImgPath);
        int faceNum = map.keySet().iterator().next();
        HashMap<String, Face> hashMap = new HashMap<>();
        int faceIndex = 1;
        for (Rect rect : map.values().iterator().next()) {
            Face face = new Face(rect.x, rect.y, rect.width, rect.height);
            hashMap.put("face" + faceIndex, face);
            faceIndex++;
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("face_number", faceNum);
        result.put("faces", hashMap);
        long endTime = System.currentTimeMillis();
        result.put("time", endTime - startTime);

        System.out.println(result);

        return renderSuccess(result, httpServletResponse);
    }

}
