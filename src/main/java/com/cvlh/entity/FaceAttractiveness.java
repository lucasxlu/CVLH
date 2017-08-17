package com.cvlh.entity;


/**
 * face attractiveness entity
 */
public class FaceAttractiveness {

    private int imageNum;
    private double attractiveness;

    public FaceAttractiveness(int imageNum, double attractiveness) {
        this.imageNum = imageNum;
        this.attractiveness = attractiveness;
    }

    public FaceAttractiveness() {
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public double getAttractiveness() {
        return attractiveness;
    }

    public void setAttractiveness(double attractiveness) {
        this.attractiveness = attractiveness;
    }

    @Override
    public String toString() {
        return "FaceAttractiveness{" +
                "imageNum=" + imageNum +
                ", attractiveness=" + attractiveness +
                '}';
    }
}
