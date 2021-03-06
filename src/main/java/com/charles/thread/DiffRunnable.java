package com.charles.thread;

import org.openjdk.jol.info.ClassLayout;

public class DiffRunnable {

    public static void main(String[] args) {
        // testThread();
        testRunnable();
    }

    private static void testThread() {
        MyThread mt1 = new MyThread();
        MyThread mt2 = new MyThread();
        MyThread mt3 = new MyThread();
        mt1.start();//每个线程都各卖了10张，共卖了30张票
        mt2.start();//但实际只有10张票，每个线程都卖自己的票
        mt3.start();//没有达到资源共享
    }

    private static void testRunnable() {
        MyRunnable mt = new MyRunnable();
        // 同一个mt，但是在Thread中就不可以，如果用同一个实例化对象mt，就会出现异常
        Thread thread1 = new Thread(mt);
        Thread thread2 = new Thread(mt);
        Thread thread3 = new Thread(mt);

        thread1.start();
        thread2.start();
        thread3.start();
    }

    // Thread类也是Runnable接口的子类
    private static class MyThread extends Thread {
        private int ticket = 10;

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                if (this.ticket > 0) {
                    Thread thread = Thread.currentThread();
                    System.out.println(String.format("[%s-%s]卖票：ticket%s", thread.getId(), thread.getName(), this.ticket--));
                }
            }
        }
    }

    private static class MyRunnable implements Runnable {
        private int ticket = 10;

        @Override
        public void run() {
            synchronized (this) {
                System.out.println(ClassLayout.parseInstance(this).toPrintable());
                for (int i = 0; i < 3; i++) {
                    if (this.ticket > 0) {
                        Thread thread = Thread.currentThread();
                        System.out.println(String.format("[%s-%s]卖票：ticket%s", thread.getId(), thread.getName(), this.ticket--));
                    }
                }
            }
        }
    }
}
