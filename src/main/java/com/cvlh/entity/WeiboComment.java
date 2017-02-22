package com.cvlh.entity;

import java.util.Date;

/**
 * Created by 29140 on 2017/2/22.
 */
public class WeiboComment {

    private String username;
    private Date commentDate;
    private String comment;
    private int likeNum;
    private String device;

    public WeiboComment() {
    }

    public WeiboComment(String username, Date commentDate, String comment, int likeNum, String device) {
        this.username = username;
        this.commentDate = commentDate;
        this.comment = comment;
        this.likeNum = likeNum;
        this.device = device;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "WeiboComment{" +
                "username='" + username + '\'' +
                ", commentDate=" + commentDate +
                ", comment='" + comment + '\'' +
                ", likeNum=" + likeNum +
                ", device='" + device + '\'' +
                '}';
    }
}
