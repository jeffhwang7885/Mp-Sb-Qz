package com.jeff.ThreadTest;

import java.util.concurrent.*;

public class CompletionServiceTest {
    public static void main (String[] args) {
        ExecutorService exector = Executors.newFixedThreadPool(3);
        try {
            CompletionService<String> completionService = new ExecutorCompletionService(exector);
            for(int i=0; i< 20; i++) {
                completionService.submit(new Worker(i));
            }

            for(int i=0; i< 20; i++) {
//                System.out.println(completionService.take().get());
                Future<String> f = completionService.poll(1, TimeUnit.SECONDS);
                if(f != null) {
                    System.out.println(f.get());
                } else {
                    System.out.println("null");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            exector.shutdown();
//            exector.shutdownNow();
        }

    }

    public static class Worker implements Callable<String> {
        private int ii;

        public Worker(int i) {
            this.ii = i;
        }

        @Override
        public String call() throws Exception {
            System.out.println("Thread:" + ii);
            Thread.sleep(2000);
            return "Ended Thread:" + ii;
        }
    }
}
