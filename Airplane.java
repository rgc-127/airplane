package com.airgame;

public class Airplane extends FlyingObject implements Enemy {

    private int speed = 8;
    private int score ;


    public Airplane(){

        super((int)(Math.random()*(Main.WIDTH - Main.airplane.getWidth())),0 - Main.airplane.getHeight(),Main.airplane,1);
        //speed
        score = 5;
    }


    @Override
    public void move() {
        //从屏幕上边缘随机x处出现，y从0开始增大，y += 5

        this.setY(this.getY() + speed) ;


    }

    @Override
    public int getScore() {
        return score;
    }
}
