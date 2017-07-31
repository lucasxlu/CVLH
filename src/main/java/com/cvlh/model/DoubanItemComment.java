package com.cvlh.model;

import java.util.Date;

public class DoubanItemComment {
    private String itemId;
    private String commenterName;
    private String commenterUrl;
    private int vote;
    private int star;
    private Date commentTime;
    private String comment;

    public DoubanItemComment() {
    }

    public DoubanItemComment(String itemId, String commenterName, String commenterUrl, int vote, int star, Date commentTime, String comment) {
        this.itemId = itemId;
        this.commenterName = commenterName;
        this.commenterUrl = commenterUrl;
        this.vote = vote;
        this.star = star;
        this.commentTime = commentTime;
        this.comment = comment;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterUrl() {
        return commenterUrl;
    }

    public void setCommenterUrl(String commenterUrl) {
        this.commenterUrl = commenterUrl;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "DoubanItemComment{" +
                "itemId='" + itemId + '\'' +
                ", commenterName='" + commenterName + '\'' +
                ", commenterUrl='" + commenterUrl + '\'' +
                ", vote=" + vote +
                ", star=" + star +
                ", commentTime=" + commentTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
