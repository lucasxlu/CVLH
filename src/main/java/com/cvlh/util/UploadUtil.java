package com.cvlh.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 上传工具类
 */
public class UploadUtil {
    /**
     * @param request
     * @param fileArr   上传的批量文件数组
     * @param directory 设置存放目录  格式"XX/XXX"
     * @return
     */
    public static List<String> batchUpload(HttpServletRequest request,
                                           MultipartFile[] fileArr, String directory) {

        List<String> pathList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String serverPath = request.getSession().getServletContext().getRealPath("/");
        serverPath = serverPath.substring(0, serverPath.lastIndexOf(File.separator));
        serverPath = serverPath.substring(0, serverPath.lastIndexOf(File.separator));
        serverPath = serverPath + "/fileUpload/" + directory + "/" + sdf.format(new Date()) + "/";
        String relativePath = "/fileUpload/" + directory + "/" + sdf.format(new Date()) + "/";
        File localFile = new File(serverPath);

        if (!localFile.exists()) {
            localFile.mkdirs();
        }

        String fileName = "";
        String filePath = "";

        for (int i = 0; i < fileArr.length; i++) {
            if (fileArr[i].isEmpty()) {
                pathList.add("");
            } else {
                fileName = fileArr[i].getOriginalFilename();
                if (fileName.trim() != "") {
                    filePath = serverPath + fileName;
                    File realfile = new File(filePath);
                    try {
                        fileArr[i].transferTo(realfile);
                        pathList.add(relativePath + fileName);
                        pathList.add(serverPath + fileName);
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return pathList;
    }

    @RequestMapping(value = "singleUpload")
    @ResponseBody
    public static String singleUpload(HttpServletRequest request, @RequestParam MultipartFile file, String directory) {
        if (directory == null) {
            directory = "images";
        }
        String finalPath = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //保存到服务器的路径（绝对路径）
        String serverPath = request.getSession().getServletContext().getRealPath("/");
        serverPath = serverPath.substring(0, serverPath.lastIndexOf(File.separator));
        serverPath = serverPath.substring(0, serverPath.lastIndexOf(File.separator));
        serverPath = serverPath + "/fileUpload/" + directory + "/" + sdf.format(new Date()) + "/";
        //返回的保存路径(相对路径)
        String relativePath = "/fileUpload/" + directory + "/" + sdf.format(new Date()) + "/";
        File localFile = new File(serverPath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        //最终路径+文件名
        File realfile = new File(serverPath + file.getOriginalFilename());
        try {
            file.transferTo(realfile);
            finalPath = relativePath + file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalPath;
    }
}
