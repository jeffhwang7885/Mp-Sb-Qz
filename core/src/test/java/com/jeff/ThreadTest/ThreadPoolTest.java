package com.jeff.ThreadTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {

    public static void main (String[] args) {
        boolean done = false;
        ExecutorService executors = null ;
        try {
            executors = Executors.newFixedThreadPool(3);
            for(int i = 0; i < 20; i++) {
                executors.execute(new Worker(i));
            }

//            executors.shutdown();
//            done = executors.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println("All threads done ? "+ done);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if(!executors.isTerminated()) {
                System.out.println("Shut down now");
                executors.shutdown();
//                executors.shutdownNow();
            }
        }
    }

    public static class Worker implements Runnable {

        private int seq;

        public Worker(int seq) {
            this.seq = seq;
        }

        @Override
        public void run() {
            System.out.println("Before sleep, seq: " + seq);
            try {
                Thread.sleep(2000);
//                int i = 1/0;
            } catch (Exception e) {
                System.out.println(e.getMessage()+ " : " + seq);
            }
            System.out.println(seq + " Ended");
        }
    }
}
