package com.cvlh.model;

import java.io.Serializable;

public class TodayEvents implements Serializable {
    private String id;

    private Integer year;

    private Integer month;

    private Integer day;

    private String title;

    private String description;

    private String thumb;

    private String url;

    private String article;

    private static final long serialVersionUID = 1L;

    public TodayEvents() {
    }

    public TodayEvents(String id, Integer year, Integer month, Integer day, String title, String description, String thumb, String url, String article) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.title = title;
        this.description = description;
        this.thumb = thumb;
        this.url = url;
        this.article = article;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb == null ? null : thumb.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article == null ? null : article.trim();
    }
}