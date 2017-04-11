package com.cvlh.model;

import java.io.Serializable;

public class HZAUFace implements Serializable {
    private String idnumber;

    private String avatarpath;

    private Double labeledScore;

    private String college;

    private String labeler;

    private static final long serialVersionUID = 1L;

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    public String getAvatarpath() {
        return avatarpath;
    }

    public void setAvatarpath(String avatarpath) {
        this.avatarpath = avatarpath == null ? null : avatarpath.trim();
    }

    public Double getLabeledScore() {
        return labeledScore;
    }

    public void setLabeledScore(Double labeledScore) {
        this.labeledScore = labeledScore;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public String getLabeler() {
        return labeler;
    }

    public void setLabeler(String labeler) {
        this.labeler = labeler == null ? null : labeler.trim();
    }
}