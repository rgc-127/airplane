package com.airgame;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {

    private int x;
    private int y;
    private BufferedImage img;
    private int width;
    private int height;
    private int life;


    private int ember;

    public int getEmber() {
        return ember;
    }

    public void setEmber(int ember) {
        this.ember = ember;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }




    FlyingObject(){

    }
    FlyingObject(int x,int y,BufferedImage img,int life){

        this.img = img;
        this.height = img.getHeight();
        this.width  = img.getWidth();
        this.x = x;
        this.y = y;
        this.life = life;
        this.ember = 0;


    }

    public abstract  void move();




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

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }



    public void minuseLife(){

        this.life --;

    }


}
