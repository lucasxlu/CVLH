package com.cvlh.model;

import java.io.Serializable;

public class GovReportHotwords implements Serializable {
    private Integer id;

    private String word;

    private Double freq;

    private Integer year;

    private static final long serialVersionUID = 1L;

    public GovReportHotwords() {
    }

    public GovReportHotwords(Integer id, String word, Double freq, Integer year) {
        this.id = id;
        this.word = word;
        this.freq = freq;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    public Double getFreq() {
        return freq;
    }

    public void setFreq(Double freq) {
        this.freq = freq;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "GovReportHotwords{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", freq=" + freq +
                ", year=" + year +
                '}';
    }
}