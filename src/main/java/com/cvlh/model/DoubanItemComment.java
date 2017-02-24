package com.cvlh.model;

import java.io.Serializable;
import java.util.Date;

public class DoubanItemComment implements Serializable {
    private String commentId;

    private String username;

    private Integer star;

    private Integer upvote;

    private Integer downvote;

    private Date commentdate;

    private String content;

    private String digest;

    public DoubanItemComment(String commentId, String username, Integer star, Integer upvote, Integer downvote, Date commentdate, String content, String digest) {
        this.commentId = commentId;
        this.username = username;
        this.star = star;
        this.upvote = upvote;
        this.downvote = downvote;
        this.commentdate = commentdate;
        this.content = content;
        this.digest = digest;
    }

    public DoubanItemComment() {
    }

    private static final long serialVersionUID = 1L;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getUpvote() {
        return upvote;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }

    public Integer getDownvote() {
        return downvote;
    }

    public void setDownvote(Integer downvote) {
        this.downvote = downvote;
    }

    public Date getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(Date commentdate) {
        this.commentdate = commentdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    @Override
    public String toString() {
        return "DoubanItemComment{" +
                "commentId='" + commentId + '\'' +
                ", username='" + username + '\'' +
                ", star=" + star +
                ", upvote=" + upvote +
                ", downvote=" + downvote +
                ", commentdate=" + commentdate +
                ", content='" + content + '\'' +
                ", digest='" + digest + '\'' +
                '}';
    }
}