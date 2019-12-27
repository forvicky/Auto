package com.zdd.autolibrary.model;

/**
 * Created by zdd on 2019/12/26
 */
public class ImgRect {
    private int x;
    private int y;
    private int width;
    private int height;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCenterX(){
        return x+width/2;
    }

    public int getCenterY(){
        return y+height/2;
    }

    @Override
    public String toString() {
        return "ImgRect{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
