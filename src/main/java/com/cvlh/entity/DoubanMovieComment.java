package com.cvlh.entity;

import java.util.Date;

/**
 * Created by 29140 on 2017/2/20.
 *
 * a java bean for a movie's comment of one specific movie
 */
public class DoubanMovieComment {

    private DoubanEr doubanEr;
    private int voteNum;
    private int starNum;
    private Date commentDate;

    public DoubanMovieComment() {
    }

    public DoubanMovieComment(DoubanEr doubanEr, int voteNum, int starNum, Date commentDate) {
        this.doubanEr = doubanEr;
        this.voteNum = voteNum;
        this.starNum = starNum;
        this.commentDate = commentDate;
    }

    public DoubanEr getDoubanEr() {
        return doubanEr;
    }

    public void setDoubanEr(DoubanEr doubanEr) {
        this.doubanEr = doubanEr;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        return "DoubanMovieComment{" +
                "doubanEr=" + doubanEr +
                ", voteNum=" + voteNum +
                ", starNum=" + starNum +
                ", commentDate=" + commentDate +
                '}';
    }
}
