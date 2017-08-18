package com.cvlh.entity;

public enum NlpJobType {
    SHIXI("shixi"), PARTTIME("parttime"), FULLTIME("fulltime");

    NlpJobType() {
    }

    private String type;

    NlpJobType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
