package com.jeff.lock;

import java.util.concurrent.locks.ReentrantLock;

public class TestReentranceLock {

    public static void main(String[] args) {
        ReentranceList list = new ReentranceList();

        Thread t = new Thread() {
            public void run() {
                list.get(this.getName());
            }
        };
        t.start();
        try {
            t.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread() {
            public void run() {
                list.get(this.getName());
            }
        };
        t2.start();


        new Thread(() -> {
            list.get("3");
        }).start();
    }

    static class ReentranceList {
        private ReentrantLock lock = new ReentrantLock();

        public void get(String id) {
            lock.lock();
            try {
                System.out.println(id);
            } finally {
                lock.unlock();
            }
        }
    }
}
