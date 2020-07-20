package com.airgame;

public class Bullet extends FlyingObject{

    private int speed;

    public Bullet(int x,int y){

        super(x , y ,Main.bullet,1);
        speed = 10;


    }



    @Override
    public void move() {
        //从英雄机的中间或者两边发出，y减少
        this.setY(this.getY() - this.getHeight() - speed) ;

    }
}
