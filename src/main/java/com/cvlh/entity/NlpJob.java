package com.cvlh.entity;

import java.util.Date;

public class NlpJob {

    private String jobName;
    private String company;
    private String location;
    private int applyNum;
    private String detailUrl;
    private Date publishDate;

    public NlpJob(String jobName, String company, String location, int applyNum, String detailUrl, Date publishDate) {
        this.jobName = jobName;
        this.company = company;
        this.location = location;
        this.applyNum = applyNum;
        this.detailUrl = detailUrl;
        this.publishDate = publishDate;
    }

    public NlpJob() {
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "NlpJob{" +
                "jobName='" + jobName + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", applyNum=" + applyNum +
                ", detailUrl='" + detailUrl + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
