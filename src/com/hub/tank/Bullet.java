package com.hub.tank;

import java.awt.*;

/**
 * @Auther: Hub
 * @Date: 2020-11-12 - 11 - 12 - 10:56
 * @Description: com.hub.tank
 * @version: 1.0
 * 子弹类
 */
public class Bullet {
    private static final int SPEED = PropertyMgr.getIntProps("bulletSpeed");
    public static int WIDTH = ResourceMgr.bulletU.getWidth();
    public static int HEIGHT = ResourceMgr.bulletU.getHeight();
    Rectangle rect = new Rectangle();

    private int x, y;
    private Dir dir;
    private Boolean live = true;
    private Group group = Group.BAD;
    TankFrame tf=null;

    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir,Group group,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x= this.x;
        rect.y = this.y;
        rect.width = Bullet.WIDTH;
        rect.height = Bullet.HEIGHT;
    }

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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!live) {
            tf.bullets.remove(this);
        }
        switch(dir){
            case UP:
                g.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
        }
        switch (dir) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
        }
        rect.x = this.x;
        rect.y = this.y;
        if (x<0||y<0||x>TankFrame.GAME_WIDTH||y>TankFrame.GAME_HEIGHT){
            this.die();
            if (dir==Dir.UP){
                tf.explodes.add(new Explode(this.x-Explode.WIDTH/2+Bullet.WIDTH/2,this.y,tf));
            }else if(dir==Dir.LEFT){
                tf.explodes.add(new Explode(this.x,this.y-Explode.HEIGHT/2+Bullet.HEIGHT/2,tf));
            }else if (dir==Dir.DOWN){
                tf.explodes.add(new Explode(this.x-Explode.WIDTH/2+Bullet.WIDTH/2,this.y-Explode.HEIGHT/2,tf));
            }else if (dir==Dir.RIGHT){
                tf.explodes.add(new Explode(this.x-Explode.WIDTH/2,this.y-Explode.HEIGHT/2+Bullet.HEIGHT/2,tf));
            }
            if(this.group==Group.GOOD){
                new Thread(()->new Audio("audio/explode.wav").play()).start();
            }

        }
    }

    public void collideWith(Tank tank) {
        if (this.group==tank.getGroup()){
            return;
        }
        if (rect.intersects(tank.rect)){
            tank.die();
            this.die();
            tf.explodes.add(new Explode(tank.getX(), tank.getY(),tf));
            new Thread(()->new Audio("audio/explode.wav").play()).start();
        }
    }

    private void die() {
        this.live = false;
    }
}
