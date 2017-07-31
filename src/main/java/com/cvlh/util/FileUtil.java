package com.cvlh.util;

import com.cvlh.model.DoubanItemComment;
import com.cvlh.model.DoubanItemReview;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by 29140 on 2017/2/26.
 */
public class FileUtil {

    /**
     * output douban <b>Review</b> list to a Excel file
     *
     * @param doubanItemReviewList
     */
    public static void outDoubanReviewExcel(List<DoubanItemReview> doubanItemReviewList, String filePath) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("first");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("评论ID");
        row.createCell(1).setCellValue("用户名");
        row.createCell(2).setCellValue("评分");
        row.createCell(3).setCellValue("被赞同数");
        row.createCell(4).setCellValue("被反对数");
        row.createCell(5).setCellValue("评论时间");
        row.createCell(6).setCellValue("评论内容");
        row.createCell(7).setCellValue("简要评论");


        for (int i = 0; i < doubanItemReviewList.size(); i++) {
            Row contentRow = sheet.createRow(i + 1);
            DoubanItemReview doubanItemComment = doubanItemReviewList.get(i);

            contentRow.createCell(0).setCellValue(doubanItemComment.getCommentId());
            contentRow.createCell(1).setCellValue(doubanItemComment.getUsername());
            contentRow.createCell(2).setCellValue(doubanItemComment.getStar());
            contentRow.createCell(3).setCellValue(doubanItemComment.getUpvote());
            contentRow.createCell(4).setCellValue(doubanItemComment.getDownvote());
            contentRow.createCell(5).setCellValue(doubanItemComment.getCommentdate());
            contentRow.createCell(6).setCellValue(doubanItemComment.getContent());
            contentRow.createCell(7).setCellValue(doubanItemComment.getDigest());

        }

        FileOutputStream fileOut = new FileOutputStream(filePath + File.separator + doubanItemReviewList.get(0).getItemId() + ".xlsx");
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * output douban <b>Comment</b> list to a Excel file
     *
     * @param doubanItemCommentList
     * @param filePath
     * @throws IOException
     */
    public static void outDoubanCommentExcel(List<DoubanItemComment> doubanItemCommentList, String filePath) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("comments");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("资源ID");
        row.createCell(1).setCellValue("用户名");
        row.createCell(2).setCellValue("用户主页");
        row.createCell(3).setCellValue("赞同数");
        row.createCell(4).setCellValue("评分");
        row.createCell(5).setCellValue("发布时间");
        row.createCell(6).setCellValue("评论内容");


        for (int i = 0; i < doubanItemCommentList.size(); i++) {
            Row contentRow = sheet.createRow(i + 1);
            DoubanItemComment doubanItemComment = doubanItemCommentList.get(i);

            contentRow.createCell(0).setCellValue(doubanItemComment.getItemId());
            contentRow.createCell(1).setCellValue(doubanItemComment.getCommenterName());
            contentRow.createCell(2).setCellValue(doubanItemComment.getCommenterUrl());
            contentRow.createCell(3).setCellValue(doubanItemComment.getVote());
            contentRow.createCell(4).setCellValue(doubanItemComment.getStar());
            contentRow.createCell(5).setCellValue(doubanItemComment.getCommentTime());
            contentRow.createCell(6).setCellValue(doubanItemComment.getComment());

        }

        FileOutputStream fileOut = new FileOutputStream(filePath + File.separator + doubanItemCommentList.get(0).getItemId() + ".xlsx");
        wb.write(fileOut);
        fileOut.close();
    }


    public static void loadImgToDb(String baseDir) throws IOException {
        Files.list(Paths.get(baseDir)).forEach(path -> {
            if (Files.isDirectory(path)) {
                try {
                    Files.list(path).forEach(path1 -> {
                        String idNumber = path1.getFileName().toString().split("\\.")[0].trim();
                        String imgPath = "../res/hzau/" + path.getFileName().toString() + "/" + path1.getFileName().toString();
                        String college = path.getFileName().toString();
                        String line = idNumber + "\t" + imgPath + "\t" + college + "\r\n";
                        System.out.println(line);
                        try {
                            Files.write(Paths.get("D:/facedata.txt"), line.getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
