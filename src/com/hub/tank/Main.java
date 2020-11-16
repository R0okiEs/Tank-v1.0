package com.hub.tank;

/**
 * @Auther: Hub
 * @Date: 2020-11-10 - 11 - 10 - 11:19
 * @Description: com.hub.tank
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        int initTankCount=PropertyMgr.getIntProps("initTankCount");

        for (int i=0;i<initTankCount;i++){
            tf.tanks.add(new Tank(200 + i*80, 200, Dir.DOWN,Group.BAD, tf));
        }

        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while(true){
            Thread.sleep(25);
            tf.repaint();
        }
        /*Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                tf.repaint();
            }
        };
        timer.schedule(timerTask,0,50);*/
    }
}
