package com.jeff.lock;

public class TestLock extends Thread{

    public void run() {
        doSomeThing();
    }

    public synchronized void doSomeThing() {
        System.out.println(this.getName());
        System.out.println(this.getName() + " End");
    }



    public static void main(String[] args) {
        TestLock lock = new TestLock();
        Thread t = new Thread(lock, "11111");
        t.start();
        Thread t2 = new Thread(lock, "22222");
        t2.start();
        System.out.println("All End");

    }
}
