package com.cabin.demo.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 100;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10_000; j++) {
                    incrementWithCAS();
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        System.err.println("Final value: " + atomicInteger.get());
    }

    private static void incrementWithCAS() {
        int failCount = 0;
        while (true) {
            int current = atomicInteger.get();
            int newValue = current + 1;
            // CAS thực hiện ở đây
            if (atomicInteger.compareAndSet(current, newValue)) {
                if( failCount > 0) {
                    System.out.println("CAS failed " + failCount + " times before succeeding.");
                }
                return;
            }
            failCount++;
        }
    }
}
