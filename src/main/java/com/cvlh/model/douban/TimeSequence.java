package com.cvlh.model.douban;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 29140 on 2017/2/25.
 */
public class TimeSequence implements Serializable {
    private int number;
    private Date date;

    public TimeSequence(int number, Date date) {
        this.number = number;
        this.date = date;
    }

    public TimeSequence() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TimeSequence{" +
                "number=" + number +
                ", date=" + date +
                '}';
    }
}
