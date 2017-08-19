package com.cvlh.model;

import java.util.List;

@SuppressWarnings("rawtypes")
public class JsonToBtable {

    long total = 0;
    boolean success = true;
    int status = 0;

    List rows = null;

    public JsonToBtable(long total, boolean success, int status, List rows) {
        super();
        this.total = total;
        this.success = success;
        this.status = status;
        this.rows = rows;
    }


    public JsonToBtable(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
