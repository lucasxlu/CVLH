package com.cvlh.entity;

/**
 * Created by 29140 on 2017/2/22.
 */
public class WeiboEr {

    private String username;
    private String sex;
    private String location;
    private String avatar;
    private String motto;
    private int weiboNum;  //all weibo numbers posted
    private int followingNum;
    private int followerNum;

    public WeiboEr() {
    }

    public WeiboEr(String username, String sex, String location, String avatar, String motto, int weiboNum, int followingNum, int followerNum) {
        this.username = username;
        this.sex = sex;
        this.location = location;
        this.avatar = avatar;
        this.motto = motto;
        this.weiboNum = weiboNum;
        this.followingNum = followingNum;
        this.followerNum = followerNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public int getWeiboNum() {
        return weiboNum;
    }

    public void setWeiboNum(int weiboNum) {
        this.weiboNum = weiboNum;
    }

    public int getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(int followingNum) {
        this.followingNum = followingNum;
    }

    public int getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    @Override
    public String toString() {
        return "WeiboEr{" +
                "username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", location='" + location + '\'' +
                ", avatar='" + avatar + '\'' +
                ", motto='" + motto + '\'' +
                ", weiboNum=" + weiboNum +
                ", followingNum=" + followingNum +
                ", followerNum=" + followerNum +
                '}';
    }
}
