package com.hub.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Hub
 * @Date: 2020-11-10 - 11 - 10 - 14:07
 * @Description: com.hub.tank
 * @version: 1.0
 */
public class TankFrame extends Frame {
    static final int GAME_WIDTH=PropertyMgr.getIntProps("gameWidth");
    static final int GAME_HEIGHT=PropertyMgr.getIntProps("gameHeight");
    Tank myTank = new Tank(400,300, Dir.UP,Group.GOOD,this);
    List<Bullet> bullets = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();

    public TankFrame(){
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setTitle("TankWar v1.0 by Hub");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            Boolean bR = false;
            Boolean bL = false;
            Boolean bU = false;
            Boolean bD = false;
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode){
                    case KeyEvent.VK_LEFT:
                        bL = true;
                        setMainTankDir();
                        new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
                        break;
                    case KeyEvent.VK_RIGHT:
                        bR = true;
                        setMainTankDir();
                        new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
                        break;
                    case KeyEvent.VK_DOWN:
                        bD = true;
                        setMainTankDir();
                        new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
                        break;
                    case KeyEvent.VK_UP:
                        bU = true;
                        setMainTankDir();
                        new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode){
                    case KeyEvent.VK_LEFT:
                        bL = false;
                        setMainTankDir();
                        break;
                    case KeyEvent.VK_RIGHT:
                        bR = false;
                        setMainTankDir();
                        break;
                    case KeyEvent.VK_DOWN:
                        bD = false;
                        setMainTankDir();
                        break;
                    case KeyEvent.VK_UP:
                        bU = false;
                        setMainTankDir();
                        break;
                    case KeyEvent.VK_CONTROL:
                        myTank.fire();
                        break;
                    default:
                        break;
                }

            }
            public void setMainTankDir(){
                if(!bR&&!bL&&!bD&&!bU){
                    myTank.setMoving(false);
                }else {
                    myTank.setMoving(true);
                    if (bL) {
                        myTank.setDir(Dir.LEFT);
                    }
                    if (bR){
                        myTank.setDir(Dir.RIGHT);
                    }
                    if (bU) {
                        myTank.setDir(Dir.UP);
                    }
                    if (bD) {
                        myTank.setDir(Dir.DOWN);
                    }
                }
            }
        });
    }

    //双缓冲处理画面闪烁问题
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics offScreen = offScreenImage.getGraphics();
        Color c = offScreen.getColor();
        offScreen.setColor(Color.BLACK);
        offScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        offScreen.setColor(c);
        paint(offScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量:" + bullets.size(), 10, 60);
        g.drawString("敌人的数量:" + tanks.size(), 10, 80);
        g.setColor(c);

        myTank.paint(g);
        for (int i=0;i<tanks.size();i++){
            tanks.get(i).paint(g);
        }
        for(int i=0;i<bullets.size();i++){
            bullets.get(i).paint(g);
        }
        for (int i=0;i<explodes.size();i++){
            explodes.get(i).paint(g);
        }
        //碰撞检测
        for (int i=0;i<bullets.size();i++){
            for (int j =0;j<tanks.size();j++){
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

    }
}
