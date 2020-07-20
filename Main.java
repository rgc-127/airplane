package com.airgame;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;


public class Main extends JPanel {


    private static ArrayList<FlyingObject> flyingObject;

    private static ArrayList<FlyingObject> flyingember;

    private static ArrayList<Bullet> bullets;

    private final Hero hero = new Hero();

    public Main() {
        flyingObject = new ArrayList<>();
        bullets = new ArrayList<>();
        flyingember = new ArrayList<>();
    }

    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage[] hero_ember = new BufferedImage[4];


    public static BufferedImage background;

    public static BufferedImage bullet;

    public static BufferedImage bee;
    public static BufferedImage[] bee_ember = new BufferedImage[4];


    public static BufferedImage airplane;
    public static BufferedImage[] airplane_ember = new BufferedImage[4];


    public static BufferedImage bigplane;
    public static BufferedImage[] bigplane_ember = new BufferedImage[4];


    public static BufferedImage gameover;
    public static BufferedImage pause;
    public static BufferedImage start;


    static {
        try {
            hero0 = ImageIO.read(Main.class.getResourceAsStream("pic/hero0.png"));
            hero1 = ImageIO.read(Main.class.getResourceAsStream("pic/hero1.png"));
            hero_ember[0] = ImageIO.read(Main.class.getResourceAsStream("pic/hero_ember0.png"));
            hero_ember[1] = ImageIO.read(Main.class.getResourceAsStream("pic/hero_ember1.png"));
            hero_ember[2] = ImageIO.read(Main.class.getResourceAsStream("pic/hero_ember2.png"));
            hero_ember[3] = ImageIO.read(Main.class.getResourceAsStream("pic/hero_ember3.png"));

            background = ImageIO.read(Main.class.getResourceAsStream("pic/background.png"));

            bullet = ImageIO.read(Main.class.getResourceAsStream("pic/bullet.png"));

            bee = ImageIO.read(Main.class.getResourceAsStream("pic/bee.png"));
            bee_ember[0] = ImageIO.read(Main.class.getResourceAsStream("pic/bee_ember0.png"));
            bee_ember[1] = ImageIO.read(Main.class.getResourceAsStream("pic/bee_ember1.png"));
            bee_ember[2] = ImageIO.read(Main.class.getResourceAsStream("pic/bee_ember2.png"));
            bee_ember[3] = ImageIO.read(Main.class.getResourceAsStream("pic/bee_ember3.png"));

            airplane = ImageIO.read(Main.class.getResourceAsStream("pic/airplane.png"));
            airplane_ember[0] = ImageIO.read(Main.class.getResourceAsStream("pic/airplane_ember0.png"));
            airplane_ember[1] = ImageIO.read(Main.class.getResourceAsStream("pic/airplane_ember1.png"));
            airplane_ember[2] = ImageIO.read(Main.class.getResourceAsStream("pic/airplane_ember2.png"));
            airplane_ember[3] = ImageIO.read(Main.class.getResourceAsStream("pic/airplane_ember3.png"));

            bigplane = ImageIO.read(Main.class.getResourceAsStream("pic/bigplane.png"));
            bigplane_ember[0] = ImageIO.read(Main.class.getResourceAsStream("pic/bigplane_ember0.png"));
            bigplane_ember[1] = ImageIO.read(Main.class.getResourceAsStream("pic/bigplane_ember1.png"));
            bigplane_ember[2] = ImageIO.read(Main.class.getResourceAsStream("pic/bigplane_ember2.png"));
            bigplane_ember[3] = ImageIO.read(Main.class.getResourceAsStream("pic/bigplane_ember3.png"));

            gameover = ImageIO.read(Main.class.getResourceAsStream("pic/gameover.png"));

            pause = ImageIO.read(Main.class.getResourceAsStream("pic/pause.png"));

            start = ImageIO.read(Main.class.getResourceAsStream("pic/start.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static final int WIDTH = 400;
    public static final int HEIGHT = 650;
    private final java.util.Timer timer = new java.util.Timer();

    public static void main(String[] args) {

        JFrame window = new JFrame("飞机大战");
        Main panel = new Main();


        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setAlwaysOnTop(true);
        window.add(panel);
        window.setUndecorated(true);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.action();

    }


    private final int START = 0;
    private final int RUNNING = 1;
    private final int PASUE = 2;
    private final int GAMEOVER = 3;


    private int state = START;

    public void action() {

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (state == START) {
                    state = RUNNING;
                } else if (state == GAMEOVER) {
                    state = START;
                    hero.setX(150);
                    hero.setY(500);
                    hero.setImg(Main.hero0);
                    hero.setLife(3);
                    hero.setSocre(0);

                }


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (state == PASUE) {
                    state = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (state == RUNNING) {
                    state = PASUE;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING) {
                    int mouse_x = e.getX();
                    int mouse_y = e.getY();

                    hero.setX(mouse_x - (hero.getWidth() / 2));
                    hero.setY(mouse_y - (hero.getHeight() / 2));
                    repaint();
                }
            }
        };

        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) {


                    creatFlyingObject();
                    outOfBoundsAction();
                    setFlyingmove();
                    setBulletsmove();

                    hero.move();
                    shootAction();
                    crash();

                }


                repaint();
            }
        }, 1000, 100);

    }

    private int flycount = 0;

    public void creatFlyingObject() {
        flycount++;
        if (flycount % 5 == 0) {
            int n = (int) (Math.random() * 10);
            FlyingObject flying;
            if (n == 0) {

                flying = new Bee();

            } else if (n == 1 || n == 2) {

                flying = new BigPlane();

            } else {
                flying = new Airplane();

            }

            flyingObject.add(flying);

        }


    }

    public void setFlyingmove() {
        for (FlyingObject object : flyingObject) {

            object.move();

        }


    }

    public void setBulletsmove() {
        for (Bullet value : bullets) {

            value.move();

        }
    }

    private int shootcount = 0;

    public void shootAction() {
        shootcount++;
        if (shootcount % 3 == 0) {
            Bullet[] bullet = hero.shoot();
            bullets.addAll(Arrays.asList(bullet));
        }

    }

    public void outOfBoundsAction() {

        for (int i = 0; i < flyingObject.size(); i++) {


            if (flyingObject.get(i).getY() > Main.HEIGHT) {

                flyingObject.remove(i);
                i--;

            }


        }
        for (int i = 0; i < bullets.size(); i++) {


            if (bullets.get(i).getY() < -bullets.get(i).getHeight()) {

                bullets.remove(i);
                i--;

            }


        }

    }

    public void crash() {

        loop1:
        for (int i = 0; i < flyingObject.size(); i++) {

            FlyingObject fly = flyingObject.get(i);

            /*if ((fly.getX() > hero.getX() && fly.getX() < (hero.getX() + hero.getWidth())) || ((fly.getX() + fly.getWidth()) > hero.getX() && (fly.getX() + fly.getWidth()) < (hero.getX() + hero.getWidth()))) {

                if (((fly.getY() + fly.getHeight()) > hero.getY() && (fly.getY() + fly.getHeight()) <= (hero.getY() + hero.getHeight())) || (fly.getY()  > hero.getY() && fly.getY()  <= (hero.getY() + hero.getHeight()) )) {


                    hero.setImg(Main.hero_ember[2]);
                    hero.setDoubleBullet(0);

//                    hero.setLife(hero.getLife() - 1);
                    hero.minuseLife();*/
            if (hero.crash(fly)){
                    if (hero.getLife() <= 0) {

                        state = GAMEOVER;
                        flyingObject.clear();
                        bullets.clear();
                        break ;

                    }
                    setScore(fly);
                    flyingember.add(flyingObject.get(i));
                    flyingObject.remove(i);
                    i--;
                    continue;
                }



            for (int j = 0; j < bullets.size(); j++) {

                Bullet bullet = bullets.get(j);

                if (bullet.getX() > fly.getX() && bullet.getX() <= (fly.getX() + fly.getWidth()) &&
                        (bullet.getY() <= (fly.getY() + fly.getHeight()) && bullet.getY() > fly.getY())) {



//                        fly.setLife(fly.getLife() - 1);
                        fly.minuseLife();
                        bullets.remove(j);
                        if (fly.getLife() == 0) {

                            setScore(fly);
                            flyingember.add(flyingObject.get(i));
                            flyingObject.remove(i);
                            i--;
                            continue loop1;
                        }
                        j --;




                }

            }


        }


    }


    public void setScore(FlyingObject fly) {

        if (fly instanceof Enemy) {

            Enemy enemy = (Enemy)fly ;

//            hero.setSocre(hero.getSocre() + 1);
            hero.addSocre(enemy.getScore());

        }
        if (fly instanceof Award) {

            Award award = (Award ) fly;

            if (award.getAward() == Award.AWARD_ADDLIFE) {

                hero.addLife();

            } else {
//                hero.setLife(hero.getLife() + 1);
                hero.setDoubleBullet(hero.getDoubleBullet() + 10);

            }
        }


    }

    public void paint(Graphics g) {
        //清除绘画内容
        super.paint(g);


        //g.drawImage(hero.getImg(),hero.getX(), hero.getY(), this);

        //设置g -> 画笔 的字体属性
        Font font = new Font("宋体", Font.BOLD, 20);
        Font font1 = new Font("宋体", Font.BOLD, 20);

        Color color = new Color(0, 0, 0);
        g.setColor(color);
        g.setFont(font);
        g.setFont(font1);

        // g.setColor(Color.yellow);


        g.drawImage(Main.background, 0, 0, this);
        g.drawString("生命值：" + hero.getLife(), 10, 20);

        g.drawString("分数：" + hero.getSocre(), 10, 40);


        g.drawImage(hero.getImg(), hero.getX(), hero.getY(), this);


        paintFlyEmber(g);
        paintFlyobject(g);
        paintBullet(g);
        paintState(g);

    }

    public void paintFlyobject(Graphics g) {

        for (int i = 0 ; i < flyingObject.size() ; i++) {
            FlyingObject flyingImg = flyingObject.get(i);
            g.drawImage(flyingImg.getImg(), flyingImg.getX(), flyingImg.getY(), this);

        }
    }

    public void paintFlyEmber(Graphics g) {

        for (int i = 0; i < flyingember.size(); i++) {

            FlyingObject flyingemberImg = flyingember.get(i);


            if (flyingemberImg instanceof Airplane) {

                if (flyingemberImg.getEmber() >= airplane_ember.length ) {
                    flyingember.remove(i);
                    i--;
                } else {
                    g.drawImage(Main.airplane_ember[flyingemberImg.getEmber()], flyingemberImg.getX(), flyingemberImg.getY(), this);
                    flyingemberImg.setEmber(flyingemberImg.getEmber() + 1);
                }


            } else if (flyingemberImg instanceof Bee) {


                if (flyingemberImg.getEmber() >= bee_ember.length) {
                    flyingember.remove(i);
                    i--;

                } else {
                    g.drawImage(Main.bee_ember[flyingemberImg.getEmber()], flyingemberImg.getX(), flyingemberImg.getY(), this);

                    flyingemberImg.setEmber(flyingemberImg.getEmber() + 1);
                }
                        /*try
                        {
                            Thread.currentThread().sleep(20);//毫秒
                        }
                        catch(Exception e){}*/


            } else {

                if (flyingemberImg.getEmber() >= bigplane_ember.length) {
                    flyingember.remove(i);
                    i--;
                } else {

                    g.drawImage(Main.bigplane_ember[flyingemberImg.getEmber()], flyingemberImg.getX(), flyingemberImg.getY(), this);

                    flyingemberImg.setEmber(flyingemberImg.getEmber() + 1);
                }

            }


        }
    }

    public void paintBullet(Graphics g) {
        for (int i = 0 ; i < bullets.size() ; i++) {

            Bullet bullet = bullets.get(i);
            g.drawImage(bullet.getImg(), bullet.getX(), bullet.getY(), this);

        }
    }


    public void paintState(Graphics g) {

        if (state == START) {

            g.drawImage(Main.start, 0, 0, this);

        } else if (state == GAMEOVER) {

            g.drawImage(Main.gameover, 0, 0, this);
        } else if (state == PASUE) {

            g.drawImage(Main.pause, 0, 0, this);
        }

    }

}


      /*  Bullet bullet = new Bullet(hero.getX() + (hero.getWidth()/2),hero.getY());
        bullets.add(bullet);
        for (int i = 0; i < airplanes.size(); i++) {

            bullets.get(i).move();

            g.drawImage(bullets.get(i ).getImg(), bullets.get(i ).getX(), bullets.get(i ).getY(), this);

        }

        while (n % 3 == 0){
            Airplane airplane = new Airplane();
            airplanes.add(airplane);
            n ++;}
            loop1:
            for (int i = 0; i < airplanes.size(); i++) {

                if (i % 5 == 0 && i > 0) {

                    Bee bee = new Bee();
                    bees.add(bee);
                    bees.get(i - 5).move();
                    g.drawImage(bees.get(i - 5).getImg(), bees.get(i - 5).getX(), bees.get(i - 5).getY(), this);
                }


                if (i % 7 == 0 && i > 0) {

                    BigPlane bigPlane = new BigPlane();
                    bigPlanes.add(bigPlane);
                    bigPlanes.get(i - 7).move();
                    g.drawImage(bigPlanes.get(i - 7).getImg(), bigPlanes.get(i - 7).getX(), bigPlanes.get(i - 7).getY(), this);
                }




                if (i == airplanes.size() - 1) {
                    for (int j = 0; j < airplanes.size() - 1; j++) {

                        if (airplanes.get(i).getX() >= airplanes.get(j).getX() && airplanes.get(i).getX() < (airplanes.get(j).getX() + airplanes.get(j).getWidth())) {

                            if ((airplanes.get(i).getY() + airplanes.get(i).getHeight() + 5) > airplanes.get(j).getY()) {

                                airplanes.remove(i);
                                break loop1;
                            }

                        } else if (airplanes.get(i).getX() + airplanes.get(i).getWidth() >= airplanes.get(j).getX() && airplanes.get(i).getX() + airplanes.get(i).getWidth() <= (airplanes.get(j).getX() + airplanes.get(j).getWidth())) {

                            if ((airplanes.get(i).getY() + airplanes.get(i).getHeight() + 5) > airplanes.get(j).getY()) {
                                airplanes.remove(i);
                                break loop1;
                            }
                        }


                    }
                }
                airplanes.get(i).move();
                g.drawImage(airplanes.get(i).getImg(), airplanes.get(i).getX(), airplanes.get(i).getY(), this);



        }


          n ++;



            for (int j = 0; j < airplanes.size(); j++) {

                airplanes.get(j).move();
                g.drawImage(airplanes.get(j).getImg(), airplanes.get(j).getX(), airplanes.get(j).getY(), this);
            }
            if (n == 1) {



                g.drawImage(hero.getImg(), hero.getX(), hero.getY(), this);

                n--;
            } else {



                g.drawImage(Main.hero1, hero.getX(), hero.getY(), this);
                n++;
            }


        Bee bee = new Bee();
        g.drawImage(bee.getImg(),bee.getX(), 10, this);

        while (true) {

          try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            Airplane airplane = new Airplane();
            airplanes.add(airplane);
            //g.drawImage(Main.background,0,0,this);
            loop1:
            for (int i = 0; i < airplanes.size(); i++) {

                if (i == airplanes.size() - 1) {
                    for (int j = 0; j < airplanes.size() - 1; j++) {

                        if (airplanes.get(i).getX() >= airplanes.get(j).getX() && airplanes.get(i).getX() < (airplanes.get(j).getX() + airplanes.get(j).getWidth())) {

                            if ((airplanes.get(i).getY() + airplanes.get(i).getHeight() + 5) > airplanes.get(j).getY()) {

                                airplanes.remove(i);
                                break loop1;
                            }

                        } else if (airplanes.get(i).getX() + airplanes.get(i).getWidth() >= airplanes.get(j).getX() && airplanes.get(i).getX() + airplanes.get(i).getWidth() <= (airplanes.get(j).getX() + airplanes.get(j).getWidth())) {

                            if ((airplanes.get(i).getY() + airplanes.get(i).getHeight() + 5) > airplanes.get(j).getY()) {
                                airplanes.remove(i);
                                break loop1;
                            }
                        }


                    }
                }
                g.clearRect(airplanes.get(i).getX(), airplanes.get(i).getY(), airplanes.get(i).getWidth(), airplanes.get(i).getHeight());
                airplanes.get(i).move();
                g.drawImage(airplanes.get(i).getImg(), airplanes.get(i).getX(), airplanes.get(i).getY(), this);

            }

            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                g.drawImage(Main.background, 0, 0, this);
                for (int j = 0; j < airplanes.size(); j++) {
                    g.clearRect(airplanes.get(j).getX(), airplanes.get(j).getY(), airplanes.get(j).getWidth(), airplanes.get(j).getHeight());
                    airplanes.get(j).move();
                    g.drawImage(airplanes.get(j).getImg(), airplanes.get(j).getX(), airplanes.get(j).getY(), this);
                }
                if (n == 1) {

                    g.clearRect(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());

                    g.drawImage(hero.getImg(), hero.getX(), hero.getY(), this);

                    n--;
                } else {

                    g.clearRect(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());

                    g.drawImage(Main.hero1, hero.getX(), hero.getY(), this);
                    n++;
                }

            }


        }

        g.drawImage(airplane.getImg(),airplane.getX(), 0, this);

         BigPlane bigplane = new BigPlane();
        g.drawImage(bigplane.getImg(),bigplane.getX(), 1000, this);

        Bullet bullet = new Bullet(hero.getX() + (hero.getWidth()/2),hero.getY());
        g.drawImage(bullet.getImg(),bullet.getX(),bullet.getY() - 50 ,this);


        g.drawImage(Main.background,0,0,this);

                g.drawImage(bullet.getImg(),bullet.getX(),bullet.getY() - 50 ,this);

                bullet.move();

                g.drawImage(bullet.getImg(),bullet.getX(),bullet.getY() - 50 ,this);



                g.clearRect(bee.getX(), bee.getY(), bee.getWidth(),bee.getHeight());

                bee.move();

                g.drawImage(bee.getImg(),bee.getX(), bee.getY(), this);


*/



