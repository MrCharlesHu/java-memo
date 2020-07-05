package com.charles.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PhaserExample {

    public static void main(String[] args) throws InterruptedException {

        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                log.info("=================step-" + phase + "===================" + registeredParties);
                return super.onAdvance(phase, registeredParties);
            }
        };

        Bus bus1 = new Bus(phaser, "小张");
        Bus bus2 = new Bus(phaser, "小李");
        Bus bus3 = new Bus(phaser, "小王");

        bus1.start();
        bus2.start();
        bus3.start();

        log.info("getRegisteredParties(): {}", phaser.getRegisteredParties());

        Thread.sleep(20000);

        log.info("getRegisteredParties(): {}, getPhaser(): {}", phaser.getRegisteredParties(), phaser.getPhase());
    }

    static public class Bus extends Thread {

        private final Phaser phaser;
        private final Random random;

        public Bus(Phaser phaser, String name) {
            this.phaser = phaser;
            setName(name);
            random = new Random();
            phaser.register();
        }

        private void trip(int sleepRange, String cityName) {
            log.info(this.getName() + " 准备去" + cityName + "....");
            int sleep = random.nextInt(sleepRange);
            try {
                TimeUnit.SECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(this.getName() + " 达到" + cityName + "...... ");
            if (this.getName().equals("小王1")) { //  测试掉队的情况
                try {
                    TimeUnit.SECONDS.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                phaser.arriveAndDeregister();
            } else {
                phaser.arriveAndAwaitAdvance();
            }
        }

        @Override
        public void run() {
            try {
                int s = random.nextInt(3);
                TimeUnit.SECONDS.sleep(s);
                log.info(this.getName() + "  准备好了，旅行路线=北京=>上海=>杭州 ");
                phaser.arriveAndAwaitAdvance();// 等待所有的汽车准备好
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            trip(5, "北京");
            trip(5, "上海");
            trip(3, "杭州");
        }
    }
}
