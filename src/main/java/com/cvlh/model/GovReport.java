package com.cvlh.model;

import java.io.Serializable;

public class GovReport implements Serializable {
    private String keyword;

    private Double frequency;

    private String chairman;

    public GovReport(String keyword, Double frequency, String chairman) {
        this.keyword = keyword;
        this.frequency = frequency;
        this.chairman = chairman;
    }

    public GovReport() {
    }

    private static final long serialVersionUID = 1L;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public String getChairman() {
        return chairman;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman == null ? null : chairman.trim();
    }

    @Override
    public String toString() {
        return "GovReport{" +
                "keyword='" + keyword + '\'' +
                ", frequency=" + frequency +
                ", chairman='" + chairman + '\'' +
                '}';
    }
}