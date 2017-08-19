package com.cvlh.model;

import java.io.Serializable;
import java.util.Date;

public class NlpJob implements Serializable {
    private Long id;

    private String jobname;

    private String company;

    private String location;

    private Integer applynum;

    private String detailurl;

    private Date publishdate;

    private String jobtype;

    private static final long serialVersionUID = 1L;

    public NlpJob() {
    }

    public NlpJob(Long id, String jobname, String company, String location, Integer applynum, String detailurl, Date publishdate, String jobtype) {
        this.id = id;
        this.jobname = jobname;
        this.company = company;
        this.location = location;
        this.applynum = applynum;
        this.detailurl = detailurl;
        this.publishdate = publishdate;
        this.jobtype = jobtype;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname == null ? null : jobname.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getApplynum() {
        return applynum;
    }

    public void setApplynum(Integer applynum) {
        this.applynum = applynum;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl == null ? null : detailurl.trim();
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    @Override
    public String toString() {
        return "NlpJob{" +
                "id=" + id +
                ", jobname='" + jobname + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", applynum=" + applynum +
                ", detailurl='" + detailurl + '\'' +
                ", publishdate=" + publishdate +
                ", jobtype='" + jobtype + '\'' +
                '}';
    }
}