package com.cvlh.entity;

public class Face {

    private double x;
    private double y;
    private double width;
    private double height;
    private double leftEyeX;
    private double leftEyeY;
    private double rightEyeX;
    private double rightEyeY;

    public Face(double x, double y, double width, double height, double leftEyeX, double leftEyeY, double rightEyeX, double rightEyeY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.leftEyeX = leftEyeX;
        this.leftEyeY = leftEyeY;
        this.rightEyeX = rightEyeX;
        this.rightEyeY = rightEyeY;
    }

    public Face() {
    }

    public Face(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLeftEyeX() {
        return leftEyeX;
    }

    public void setLeftEyeX(double leftEyeX) {
        this.leftEyeX = leftEyeX;
    }

    public double getLeftEyeY() {
        return leftEyeY;
    }

    public void setLeftEyeY(double leftEyeY) {
        this.leftEyeY = leftEyeY;
    }

    public double getRightEyeX() {
        return rightEyeX;
    }

    public void setRightEyeX(double rightEyeX) {
        this.rightEyeX = rightEyeX;
    }

    public double getRightEyeY() {
        return rightEyeY;
    }

    public void setRightEyeY(double rightEyeY) {
        this.rightEyeY = rightEyeY;
    }

    @Override
    public String toString() {
        return "Face{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", leftEyeX=" + leftEyeX +
                ", leftEyeY=" + leftEyeY +
                ", rightEyeX=" + rightEyeX +
                ", rightEyeY=" + rightEyeY +
                '}';
    }
}
