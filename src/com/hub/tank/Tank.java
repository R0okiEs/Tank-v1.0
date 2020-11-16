package com.hub.tank;

import java.awt.*;
import java.util.Random;

/**
 * @Auther: Hub
 * @Date: 2020-11-12 - 11 - 12 - 9:32
 * @Description: com.hub.tank
 * @version: 1.0
 * 坦克类
 */
public class Tank {
    private int x, y;
    private Dir dir;
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();
    private Group group = Group.BAD;
    TankFrame tf = null;
    Random r = new Random();
    private Boolean moving = true;
    private static final int SPEED = PropertyMgr.getIntProps("tankSpeed");
    private Boolean live = true;
    Rectangle rect = new Rectangle();

    public Tank() {
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x = this.x-5;
        rect.y = this.y-3;
        rect.width = Tank.WIDTH-10;
        rect.height = Tank.HEIGHT-5;
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

    public Boolean getMoving() {
        return moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!live) {
            tf.tanks.remove(this);
        }
        switch (dir) {
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
        }

        move();
    }

    private void move() {
        if (!moving) {
            return;
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

        rect.x = this.x-5;
        rect.y = this.y-3;

        if (r.nextInt(100) > 98 && this.group == Group.BAD) {
            fire();
        }
        if (r.nextInt(100) > 95 && this.group == Group.BAD) {
            randomDir();
        }
        boundsCheck();

    }

    private void boundsCheck() {
        if (this.x < 2) {
            x = 2;
        }
        if (this.y < 28) {
            y = 28;
        }
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) {
            x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        }
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) {
            y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
        }
    }

    private void randomDir() {
        dir = Dir.values()[r.nextInt(4)];
    }

    public void fire() {
        int x = 0, y = 0;
        switch (this.dir) {
            case UP:
                x = this.x + this.WIDTH / 2 - Bullet.WIDTH / 2;
                y = this.y;
                break;
            case DOWN:
                x = this.x + this.WIDTH / 2 - Bullet.WIDTH / 2;
                y = this.y + this.HEIGHT;
                break;
            case RIGHT:
                x = this.x + this.WIDTH;
                y = this.y + this.HEIGHT / 2 - Bullet.HEIGHT / 2;
                break;
            case LEFT:
                x = this.x - 20;
                y = this.y + this.HEIGHT / 2 - Bullet.HEIGHT / 2;
                break;
        }
        tf.bullets.add(new Bullet(x, y, this.dir, this.group, this.tf));

        if (this.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();

    }

    public void die() {
        this.live = false;
    }
}

