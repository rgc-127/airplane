package com.airgame;

public class Bee extends FlyingObject implements Award{


    private int x_speed ;
    private int y_speed ;
    private int award ;
    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }



    public Bee(){
        super((int)(Math.random()*Main.WIDTH -Main.bee.getWidth()),0 - Main.bee.getHeight(),Main.bee,1);

        x_speed = 3;
        y_speed = 3;

        award = (int) (Math.random()*2);


    }

    @Override
    public void move() {
        //从屏幕边缘随机x处出现，x左右摇摆，y从0开始增大，每次y++,打掉会获得随机奖励，子弹+1或者生命+1.子弹最多两个


        this.setY(this.getY() + y_speed) ;
        this.setX(this.getX() + x_speed) ;
        if (this.getX() > Main.WIDTH - this.getWidth()){

            x_speed = -3 ;

        }if (this.getX() < 0){

            x_speed = 3 ;

        }


    }
}
