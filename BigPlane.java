package com.airgame;

public class BigPlane extends FlyingObject implements Award, Enemy {


    private int speed;

    private int award ;
    private int score ;

    public BigPlane() {

        super((int) (Math.random() * (Main.WIDTH - Main.bigplane.getWidth())), 0 - Main.bigplane.getHeight(), Main.bigplane, 5);

        speed = 5;
        award = (int) (Math.random()*2);
        score = 10;

    }


    @Override
    public void move() {
        //从屏幕边缘随机x处出现，y从0开始增大，每次y++
        this.setY(this.getY() + speed);

    }

    @Override
    public int getAward() {
        return award;
    }

    @Override
    public int getScore() {
        return score;
    }
}
