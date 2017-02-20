package com.cvlh.entity;

/**
 * Created by 29140 on 2017/2/20.
 *
 * a java bean for douban user
 */
public class DoubanEr {

    private String username;
    private String avatar;
    private String userPage;

    public DoubanEr() {
    }

    public DoubanEr(String username, String avatar, String userPage) {
        this.username = username;
        this.avatar = avatar;
        this.userPage = userPage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserPage() {
        return userPage;
    }

    public void setUserPage(String userPage) {
        this.userPage = userPage;
    }

    @Override
    public String toString() {
        return "DoubanEr{" +
                "username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userPage='" + userPage + '\'' +
                '}';
    }
}
