package com.airgame;


import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{


    private int socre;
    private int count;
    private int doubleBullet;

    public int getDoubleBullet() {
        return doubleBullet;
    }

    public void setDoubleBullet(int doubleBullet) {
        this.doubleBullet = doubleBullet;
    }




    private BufferedImage[] heroImg = {Main.hero0,Main.hero1};

    public Hero(){

        //初始位于窗口下部中间，有初始生命，初始分数为0

       super(150,500,Main.hero0,3);



       //bullet[0] = new Bullet((this.getX())/2,this.getY());

    }

    //发射子弹
    public Bullet[] shoot(){

         if(doubleBullet > 0 ){

            Bullet[] bullet = new Bullet[2];

            bullet[0] = new Bullet (this.getX() + 10,this.getY() - Main.bullet.getHeight());

            bullet[1] = new Bullet (this.getX() +this.getWidth() - 10,this.getY() - Main.bullet.getHeight());
            doubleBullet--;
            return   bullet;

        } else{
            final int HERO_OFFEST = 3;

            Bullet[] bullet = new Bullet[1];

            bullet[0] = new Bullet (this.getX() + (this.getWidth()/2) - HERO_OFFEST,this.getY() - Main.bullet.getHeight());

            return bullet;

        }
    }

    @Override
    public void move() {

        //跟随鼠标移动

        count ++;
        this.setImg(heroImg[count % 2]);
        /*if (count % 10 == 0) {
            this.setX((int) (Math.random() * (Main.WIDTH - Main.hero0.getWidth())));
        }*/
        /*if (count % 2  == 0){

            this.setImg(Main.hero0) ;
            count ++;
        }else {
            this.setImg(Main.hero1) ;


        }*/



    }



    public int getSocre() {
        return socre;
    }

    public void setSocre(int socre) {
        this.socre = socre;
    }

    public void addLife() {

        this.setLife(this.getLife() + 1);

    }
    public void addSocre(int socre) {

        this.setSocre(this.getSocre() + socre);

    }
    public boolean crash(FlyingObject fly){

        if (((fly.getX() > this.getX() && fly.getX() < (this.getX() + this.getWidth())) || ((fly.getX() + fly.getWidth()) > this.getX() && (fly.getX() + fly.getWidth()) < (this.getX() + this.getWidth()))) &&
             ( ((fly.getY() + fly.getHeight()) > this.getY() && (fly.getY() + fly.getHeight()) <= (this.getY() + this.getHeight())) || (fly.getY()  > this.getY() && fly.getY()  <= (this.getY() + this.getHeight()) ))) {

//            ((fly.getY() + fly.getHeight()) > this.getY() && (fly.getY() + fly.getHeight()) <= (this.getY() + this.getHeight())) || (fly.getY()  > this.getY() && fly.getY()  <= (this.getY() + this.getHeight()) )


                this.setImg(Main.hero_ember[2]);
                this.setDoubleBullet(0);

//                    hero.setLife(hero.getLife() - 1);
                this.minuseLife();
                return true;

            }else{
            return false;
        }

    }

}
