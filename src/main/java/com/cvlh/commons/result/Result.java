package com.cvlh.commons.result;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/18.
 */
public class Result implements Serializable {
    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private int status;

    private String msg = "";

    private Object result = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
